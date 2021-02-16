package za.luna;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/resteasy-jackson/Grass")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JacksonResource {

    @Inject
    DemoService demoService;


    @GET
    public List<Grass> list() {
        return demoService.getAll();
    }

    @POST
    public Grass add(Grass grass) {
        return demoService.create(grass);
    }

    @DELETE
    public Grass delete(Grass grass) {
        return demoService.remove(grass);
    }

}
