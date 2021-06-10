package com.nssp.aboutnothing.entrypoint.http;

import com.nssp.aboutnothing.data.Quote;
import com.nssp.aboutnothing.usecase.QuoteUpload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/quote")
public class QuotesController {

    private QuoteUpload quoteUpload;

    public QuotesController(QuoteUpload quoteUpload) {
        this.quoteUpload = quoteUpload;
    }

    @PostMapping
    public String post(@RequestBody Quote quote) {
        this.quoteUpload.UploadSingleFile(quote);
        return "ok";
    }
}
