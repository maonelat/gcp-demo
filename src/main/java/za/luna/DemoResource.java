package za.luna;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@IfBuildProperty(name = "hello.enabled", stringValue = "true")
public class DemoResource {

    public DemoResource(){
        String name = "";
        System.out.println(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
}