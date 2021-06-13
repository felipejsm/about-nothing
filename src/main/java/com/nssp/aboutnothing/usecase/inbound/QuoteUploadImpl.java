package com.nssp.aboutnothing.usecase.inbound;

import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class QuoteUploadImpl implements QuoteUpload {
    private S3AsyncClient s3AsyncClient;
    private S3Client s3Client;
    private S3ClientConfiguration s3Configuration;
    public QuoteUploadImpl(S3ClientConfiguration s3Configuration) {
        this.s3Configuration = s3Configuration;
        this.s3AsyncClient = this.s3Configuration.getAsyncS3Client();
    }
    @Value("classpath:george_and_seinfeld_cafe.png")
    public Resource myFile;
    @Override
    public Quote UploadSingleFile(Quote quotes) {
        Map<String, String> myMap = new HashMap<>();
        myMap.put("meta1", "data1");
        myMap.put("meta2", "data2");
        quotes.id = UUID.randomUUID().toString();
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(this.s3Configuration.getBucket())
                .key(quotes.id)// ocorrencia + funcional + inclusao
                .contentType(quotes.contentType)
                .metadata(myMap)
                .build();
        try {
            CompletableFuture<PutObjectResponse> future = this.s3AsyncClient.putObject(
                    putOb, AsyncRequestBody.fromBytes(quotes.physical.getBytes())
            );
            future.whenComplete((resp, err) -> {
                try {
                    if (resp != null) {
                        System.out.println("Uploaded!");
                    } else {
                        System.err.println(err.getMessage());
                    }
                } finally {
                    s3AsyncClient.close();
                }
            });
            future.join();
        } catch (IOException ioException) {

        }
        return quotes;
    }


    @Override
    public void UploadMultipleFiles(List<Quote> quotes) {
        List<File> files = new ArrayList<>();
        for(Quote q : quotes) {
            files.add(new File("."));
        }

        TransferManager transferManager = TransferManagerBuilder.standard().build();
        try {
            MultipleFileUpload mfp = transferManager.uploadFileList(
                    this.s3Configuration.getBucket(),
                    "quotes-",
                    new File("."),
                    files);
            do {
                var progress = mfp.getProgress();
                long so_far = progress.getBytesTransferred();
                long total = progress.getTotalBytesToTransfer();
                double pct = progress.getPercentTransferred();
                System.out.println("so far "+so_far);
                System.out.println("total "+total);
                System.out.println("pct "+pct);
            } while(!mfp.isDone());
            Transfer.TransferState xfer_state = mfp.getState();
            System.out.println(": " + xfer_state);
        } catch(Exception e) {

        }
    }
}
