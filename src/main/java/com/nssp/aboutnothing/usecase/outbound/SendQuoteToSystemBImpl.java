package com.nssp.aboutnothing.usecase.outbound;

import com.nssp.aboutnothing.configuration.HttpClientConfiguration;
import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendQuoteToSystemBImpl implements SendQuoteToSystemB {


    private HttpClientConfiguration httpClientConfiguration;
    private HttpClient httpClient;
    public SendQuoteToSystemBImpl(HttpClientConfiguration httpClientConfiguration) {
        this.httpClientConfiguration = httpClientConfiguration;
        this.httpClient = this.httpClientConfiguration.getHttpClient();
    }


    @Override
    public void sendQuote(Quote quote) {
        Map<Object, Object> body = new HashMap<>();
        body.put("meta", "data");

        HttpRequest request = HttpRequest.newBuilder()
                .POST(this.httpClientConfiguration.ofFormData(body))
                .uri(this.httpClientConfiguration.getUri())
                .setHeader("User-Agent", "whatever")
                .header("name","")
                .build();
        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ioe) {

        }


    }
}
