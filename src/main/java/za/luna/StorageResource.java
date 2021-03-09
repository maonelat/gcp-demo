package za.luna;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import za.luna.service.StorageService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Slf4j
@Path("/storage")
@Produces(MediaType.APPLICATION_JSON)
public class StorageResource {

    @Inject
    StorageService storageService;

    @SneakyThrows
    @Path("/upload/")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String upload(@MultipartForm FileUploadForm data){
        return storageService.upload(data.getFileName(),data.getFile());
    }

    @GET
    public List<String> getAll(){
        return storageService.getAll();
    }

    @GET
    @Path("/{filePath:.+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("filePath") String filePath){
        return Response.ok(storageService.getFile(filePath), MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + filePath + "\"")
                .build();
    }
}
