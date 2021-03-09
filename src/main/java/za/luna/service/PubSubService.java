package za.luna.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
@ApplicationScoped
public class PubSubService {

    @ConfigProperty(name = "gcp.project-id")
    String projectId;

    public void publish(String topicName, String message) throws IOException, InterruptedException {
//        "projects/web-apps-278418/topics/myTopic"
        Publisher publisher = Publisher.newBuilder(TopicName.of(projectId,topicName)).build();// Init a publisher to the topic

        try {
            ByteString data = ByteString.copyFromUtf8(message+" @ "+ LocalDateTime.now());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {
                public void onSuccess(String messageID) {
                    log.info("published with message id {}", messageID);
                }

                public void onFailure(Throwable t) {
                    log.warn("failed to publish: {0}", t);
                }
            }, MoreExecutors.directExecutor());
        } finally {
            publisher.shutdown();
            publisher.awaitTermination(5, TimeUnit.MINUTES);
        }
        log.info("here now");
    }

}
