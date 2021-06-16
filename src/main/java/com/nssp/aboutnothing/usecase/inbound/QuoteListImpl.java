package com.nssp.aboutnothing.usecase.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nssp.aboutnothing.configuration.S3ClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

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
        this.s3Client = this.s3ClientConfiguration.getS3Client();
    }
    private Page<Quote> toPage(List<Quote> list, Pageable pageable) {
        int totalPages = list.size() / pageable.getPageSize();
        int max = pageable.getPageNumber() >= totalPages ? list.size() : pageable.getPageSize() *(pageable.getPageNumber()+1);
        int min = pageable.getPageNumber() > totalPages ? max : pageable.getPageSize()*pageable.getPageNumber();
        Page<Quote> pageResponse = new PageImpl<>(list.subList(min, max), pageable, list.size());
        return pageResponse;
    }
    @Override
    public Page<Quote> listQuotes(String key, Pageable pageable) {
        List<String> listaDeChaves = new ArrayList<>();
        Stream<S3Object> lista =
        this.s3Client.listObjectsV2Paginator(ListObjectsV2Request.builder()
                .bucket(this.s3ClientConfiguration.getBucket())
                .build())
                .stream()
                .map(ListObjectsV2Response::contents)
                .flatMap(List::stream)
                .filter(s3Object -> {
                    return s3Object.key().startsWith(key);
                });
        List<Quote> quotes = new ArrayList<>();
        lista.forEach(l -> {
            System.out.println(l.key());
            quotes.add(unique(l.key()));
        });
        return toPage(quotes, pageable);
    }
    private Quote unique(String key) {
        HeadObjectRequest headReq = HeadObjectRequest.builder()
                .bucket(this.s3ClientConfiguration.getBucket())
                .key(key)
                .build();

        Map<String, String> metadata = s3Client.headObject(headReq).metadata();
        ObjectMapper objectMapper = new ObjectMapper();
        Quote json = new Quote();
        try {
            json = objectMapper.convertValue(metadata, Quote.class);
        } catch(Exception e) {

        }
        return json;
    }
    @Override
    public Quote uniqueQuote(String key) {
        Quote quote = unique(key);
        return quote;
    }
}
