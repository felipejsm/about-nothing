package com.nssp.aboutnothing.entrypoint.http;

import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/quotes_metadata")
public class QuoteMetadataController {
    @GetMapping
    public ResponseEntity<Quote> getQuotesMetadata() {
        return null;
    }

}
