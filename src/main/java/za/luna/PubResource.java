package za.luna;

import lombok.SneakyThrows;
import za.luna.service.PubSubService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/pub")
public class PubResource {

    @Inject
    PubSubService pubSubService;

    @SneakyThrows
    @Path("/{topic}")
    @POST
    public String publishMessage(@PathParam("topic")String topic,String message){
        pubSubService.publish(topic, message);
        return "Done";
    }
}
