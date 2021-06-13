package com.nssp.aboutnothing.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration
@Data
public class S3ClientConfiguration {

    @Value("${aws.s3.uri}")
    private URI uri;

    @Value("${aws.region}")
    private Region region;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Bean
    public S3Client getS3Client() {
        S3ClientBuilder s3ClientBuilder = S3Client.builder();
        if(this.uri != null) {
            s3ClientBuilder.endpointOverride(uri);
        }
        return s3ClientBuilder.region(this.region)
                .build();
    }
    @Bean
    public AmazonS3 getAmazonS3() {

        var s3 = AmazonS3ClientBuilder.standard();
        if(this.uri != null) {
                s3.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(this.uri.toString(), this.region.toString()));
        }
        return s3.build();
    }

    @Bean
    public S3AsyncClient getAsyncS3Client() {
        var s3AsyncClientBuilder = S3AsyncClient.builder();
        if(this.uri != null) {
            s3AsyncClientBuilder.endpointOverride(this.uri);
        }
        return s3AsyncClientBuilder.region(this.region)
                .build();
    }
}
