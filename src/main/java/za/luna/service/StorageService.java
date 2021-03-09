package za.luna.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@ApplicationScoped
public class StorageService {
    @Inject
    Storage storage;

    @ConfigProperty(name = "gcp.project-id")
    String projectId;

    @ConfigProperty(name = "gcp.bucket")
    String bucketName;

    public String upload(String fileName, File file) throws FileNotFoundException {
//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Bucket bucket = getBucket();
        Blob blob = bucket.create(fileName,new FileInputStream(file), Bucket.BlobWriteOption.doesNotExist());
        return blob.getGeneratedId();
    }

    private Bucket getBucket() {
        Bucket bucket = storage.get(bucketName, Storage.BucketGetOption.userProject(projectId));
        return bucket;
    }

    public List<String> getAll() {
        Bucket bucket = getBucket();
        Iterable<Blob> iterable = bucket.list(Storage.BlobListOption.userProject(projectId)).iterateAll();
        return StreamSupport.stream(iterable.spliterator(), false)
            .map(blob -> {
                log.info("File {} uploaded on {}.", blob.getName(), blob.getUpdateTime());
                return blob.getName();
            })
            .collect(Collectors.toList());
    }

    public byte[] getFile(String filePath) {
        Bucket bucket = getBucket();
        Blob blob = bucket.get(filePath, Storage.BlobGetOption.userProject(projectId));
        return blob.getContent(Blob.BlobSourceOption.userProject(projectId));
    }
}
