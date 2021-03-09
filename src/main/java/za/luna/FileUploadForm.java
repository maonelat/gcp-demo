package za.luna;

import lombok.Data;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import java.io.File;

@Data
public class FileUploadForm {

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    private String fileName;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private File file;

}
