package com.nssp.aboutnothing.usecase;

import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.Quote;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class QuoteUploadImpl implements QuoteUpload {
    private S3AsyncClient s3AsyncClient;
    private S3ClientConfiguration s3Configuration;
    public QuoteUploadImpl(S3ClientConfiguration s3Configuration) {
        this.s3Configuration = s3Configuration;
    }

    @Override
    public void UploadSingleFile(Quote quotes) {
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(this.s3Configuration.getBucket())
                .key(quotes.name)
                .contentType(quotes.contentType)//"image/jpeg")
                .build();
        CompletableFuture<PutObjectResponse> future = this.s3AsyncClient.putObject(
                putOb, AsyncRequestBody.fromFile(quotes.physical)
        );
        future.whenComplete((resp, err) -> {
            try {
                if(resp != null) {
                    System.out.println("Uploaded!");
                } else {
                    System.err.println(err.getMessage());
                }
            } finally {
                s3AsyncClient.close();
            }
        });
        future.join();
    }

    @Override
    public void UploadMultipleFiles(List<Quote> quotes) {

    }
}
