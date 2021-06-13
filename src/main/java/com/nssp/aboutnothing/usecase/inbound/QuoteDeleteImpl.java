package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuoteDeleteImpl implements QuoteDelete{
    private S3AsyncClient s3AsyncClient;
    private S3Client s3Client;
    private S3ClientConfiguration s3Configuration;
    public QuoteDeleteImpl(S3ClientConfiguration s3Configuration) {
        this.s3Configuration = s3Configuration;
        this.s3AsyncClient = this.s3Configuration.getAsyncS3Client();
    }
    @Override
    public void deleteSingleFile(String key) {
        String objectName = key;
        ArrayList<ObjectIdentifier> toDelete = new ArrayList<>();
        toDelete.add(ObjectIdentifier.builder().key(objectName).build());
        try {
            DeleteObjectsRequest req = DeleteObjectsRequest.builder()
                    .bucket(this.s3Configuration.getBucket())
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();
            this.s3Client.deleteObjects(req);
        } catch(S3Exception s3Exception) {
            System.out.println("uh oh");
        }
    }

    @Override
    public void deleteMultipleFiles(List<Quote> quotes) {

    }
}
