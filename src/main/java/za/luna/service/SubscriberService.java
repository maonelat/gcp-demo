package za.luna.service;

import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.*;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@ApplicationScoped
public class SubscriberService {


    @ConfigProperty(name = "gcp.project-id")
    String projectId;


    public void listen(@Observes StartupEvent event) throws IOException {
        TopicName topicName = TopicName.of(projectId, "myTopic");
        ProjectSubscriptionName subscriptionName = initSubscription("myTopic-sub",topicName);
        // Subscribe to PubSub
        MessageReceiver receiver = (message, consumer) -> {
            log.info("Got message {}", message.getData().toStringUtf8());
            consumer.ack();
        };
        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();
    }


    private ProjectSubscriptionName initSubscription(String subscription, TopicName topicName) throws IOException {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscription);
        try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
            Iterable<Subscription> subscriptions = subscriptionAdminClient.listSubscriptions(ProjectName.of(projectId))
                    .iterateAll();
            Optional<Subscription> existing = StreamSupport.stream(subscriptions.spliterator(), false)
                    .filter(sub -> sub.getName().equals(subscriptionName.toString()))
                    .findFirst();
            if (!existing.isPresent()) {
                subscriptionAdminClient.createSubscription(subscriptionName, topicName, PushConfig.getDefaultInstance(), 0);
            }
        }
        return subscriptionName;
    }
}
