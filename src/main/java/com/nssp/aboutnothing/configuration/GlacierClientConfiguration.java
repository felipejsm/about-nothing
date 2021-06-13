package com.nssp.aboutnothing.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glacier.GlacierClient;

import java.net.URI;

//@Configuration
//@Data
public class GlacierClientConfiguration {

    @Value("${aws.glacier.uri}")
    private URI uri;
    @Value("${aws.glacier.accountid}")
    private String accountId;

    @Value("${aws.region}")
    private Region region;
/*
    @Bean
    public GlacierClient getClient() {
        var glacier = GlacierClient.builder();
        if(this.uri != null) {
            glacier.endpointOverride(this.uri);
        }
        return glacier.region(this.region).build();
    }

 */
}
