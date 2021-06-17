package com.nssp.aboutnothing.usecase.inbound;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.Transfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
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
    private AmazonS3 amazonS3;
    public QuoteUploadImpl(S3ClientConfiguration s3Configuration, AmazonS3 amazonS3) {
        this.s3Configuration = s3Configuration;
        this.s3AsyncClient = this.s3Configuration.getAsyncS3Client();
        this.amazonS3 = amazonS3;

    }
    @Value("classpath:george_and_seinfeld_cafe.png")
    public Resource myFile;
    @Override
    public Quote UploadSingleFile(Quote quotes) {
        var uui = UUID.randomUUID().toString();
        quotes.setId("fileA/".concat(uui));
        /*
        private String id;
    private String name;
    private String contentType;
    private String description;
        * */
        Map<String, String> myMap = new HashMap<>();
        myMap.put("id", quotes.getId());
        myMap.put("name", quotes.getName());
        myMap.put("contentType", quotes.getContentType());
        myMap.put("description", quotes.getDescription());
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(this.s3Configuration.getBucket())
                .key(quotes.getId())
                .contentType(quotes.getContentType())
                .metadata(myMap)
                .build();
        try {
            CompletableFuture<PutObjectResponse> future = this.s3AsyncClient.putObject(
                    putOb, AsyncRequestBody.fromBytes(quotes.getPhysical().getBytes())
            );
            future.whenComplete((resp, err) -> {
                try {
                    if (resp != null) {
                        System.out.println("Uploaded!");
                    } else {
                        System.err.println(err.getMessage());
                    }
                } finally {
                    //s3AsyncClient.close();
                }
            });
            future.join();
        } catch (IOException ioException) {

        }
        quotes.setPhysical(null);
        return quotes;
    }
    @Override
    public String uploadMultipleFiles2(List<MultipartFile> multipartFilesList) {
        List<File> files = new ArrayList<>();
        for(MultipartFile mfl : multipartFilesList) {
            File newFile = new File (System.getProperty("user.dir").concat("\\").concat(mfl.getOriginalFilename()));
            try {
                mfl.transferTo(newFile);
            } catch(IOException ioe) {

            }
            files.add(newFile);
        }
        TransferManager transferManager = TransferManagerBuilder
                .standard()
                .withS3Client(this.amazonS3).build();
        try {
            MultipleFileUpload mfp = transferManager.uploadFileList(
                    this.s3Configuration.getBucket(),
                    "",
                    files.get(0).getParentFile(),
                    //new File(System.getProperty("user.dir")),
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
        return "ok";
    }
    private void insere(Quote quotes) {
        var uui = UUID.randomUUID().toString();
        quotes.setId("fileA/".concat(uui));
        Map<String, String> myMap = new HashMap<>();
        myMap.put("id", quotes.getId());
        myMap.put("name", quotes.getName());
        myMap.put("contentType", quotes.getContentType());
        myMap.put("description", quotes.getDescription());
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(this.s3Configuration.getBucket())
                .key(quotes.getId())
                .contentType(quotes.getContentType())
                .metadata(myMap)
                .build();
        try {
            CompletableFuture<PutObjectResponse> future = this.s3AsyncClient.putObject(
                    putOb, AsyncRequestBody.fromBytes(quotes.getPhysical().getBytes())
            );
            future.whenComplete((resp, err) -> {
                try {
                    if (resp != null) {
                        System.out.println("Uploaded! -> "+ quotes.getId());
                    } else {
                        System.err.println(err.getMessage());
                    }
                } finally {
                    //s3AsyncClient.close();
                }
            });
            future.join();
        } catch (IOException ioException) {

        }
    }
    @Override
    public String uploadMult(List<Quote> quotes) {
        quotes.parallelStream().forEach( q ->
                insere(q)
        );
        return "ok";
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
