package com.nssp.aboutnothing.usecase.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class QuoteListImpl implements QuoteList {
    private S3ClientConfiguration s3ClientConfiguration;
    private S3Client s3Client;
    public QuoteListImpl(S3ClientConfiguration s3ClientConfiguration) {
        this.s3ClientConfiguration = s3ClientConfiguration;
        this.s3Client = this.s3ClientConfiguration.getClient();
    }

    @Override
    public List<Quote> listQuotes(String key) {
        List<String> listaDeChaves = new ArrayList<>();
        Stream<S3Object> lista =
        this.s3Client.listObjectsV2Paginator(ListObjectsV2Request.builder()
                .bucket(this.s3ClientConfiguration.getBucket())
                .build())
                .stream()
                .map(ListObjectsV2Response::contents)
                .flatMap(List::stream)
                .filter(s3Object -> {
                    return s3Object.key().equals(key);
                });

        HeadObjectRequest headReq = HeadObjectRequest.builder()
                .key(key)
                .build();

        Map<String, String> metadata = s3Client.headObject(headReq).metadata();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Quote json = objectMapper.convertValue(metadata, Quote.class);
        } catch(Exception e) {

        }
        return null;
    }
}
