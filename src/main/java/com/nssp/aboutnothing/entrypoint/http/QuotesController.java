package com.nssp.aboutnothing.entrypoint.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nssp.aboutnothing.data.model.Pun;
import com.nssp.aboutnothing.data.model.Quote;
import com.nssp.aboutnothing.usecase.inbound.QuoteDelete;
import com.nssp.aboutnothing.usecase.inbound.QuoteList;
import com.nssp.aboutnothing.usecase.inbound.QuoteUpload;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/quote")
public class QuotesController {

    private QuoteUpload quoteUpload;
    private QuoteList quoteList;
    private QuoteDelete quoteDelete;

    public QuotesController(QuoteUpload quoteUpload, QuoteList quoteList, QuoteDelete quoteDelete) {
        this.quoteUpload = quoteUpload;
        this.quoteList = quoteList;
        this.quoteDelete = quoteDelete;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public String post(@RequestPart("quote") String quote, @RequestPart("physical") MultipartFile physical) {
        Quote q = new Quote();
        try{
            var om = new ObjectMapper();
            q = om.readValue(quote, Quote.class);
            q.physical = physical;
        } catch(IOException ioe) {

        }
        this.quoteUpload.UploadSingleFile(q);
        return "ok";
    }
    @PostMapping("/mult")
    public String mult(@RequestParam("physical") MultipartFile[] files, @RequestBody List<Quote> quotes) {
        this.quoteUpload.UploadMultipleFiles(quotes);
        return "ok";
    }
    @PostMapping(value= "/mult2")
    public String mult2(@RequestBody List<Pun> pun) {
        return "ok";
    }

    @PostMapping("/quotes")
    public ResponseEntity<Quote> postQuote(@RequestBody Quote quotes) {
        var q = this.quoteUpload.UploadSingleFile(quotes);
        return ResponseEntity.created(URI.create("/quotes/"+q.id)).body(q);
    }

    @GetMapping("/quotes/{id_quote}")
    public ResponseEntity<Quote> getQuote(@PathVariable String idQuote) {
        var q = this.quoteList.uniqueQuote(idQuote);
        return ResponseEntity.ok(q);
    }

    @DeleteMapping("/quotes/{id_quote}")
    public ResponseEntity deleteQuote(@PathVariable String idQuote) {
        this.quoteDelete.deleteSingleFile(idQuote);
        return ResponseEntity.accepted().build();
    }
}
