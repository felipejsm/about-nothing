package com.nssp.aboutnothing.entrypoint.http;

import com.amazonaws.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nssp.aboutnothing.data.model.Quote;
import com.nssp.aboutnothing.usecase.inbound.QuoteUpload;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/batch_quotes")
public class BatchQuotesController {
    private QuoteUpload quoteUpload;
    public BatchQuotesController(QuoteUpload quoteUpload) {
        this.quoteUpload = quoteUpload;
    }
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> post(@RequestParam("files") List<MultipartFile> arquivos,
                                       @RequestParam("json") String json) throws JsonProcessingException {
        System.out.println(arquivos);
        var quotesList = stringListToQuoteList(json);
        for (int i = 0; i < arquivos.size(); i++) {
            quotesList.get(i).setPhysical(arquivos.get(i));
        }

        this.quoteUpload.uploadMult(quotesList);

        return ResponseEntity.ok().build();
    }
    private List<Quote> stringListToQuoteList(String json) throws JsonProcessingException {
        List<Quote> quotes = new ArrayList<>();
        var om = new ObjectMapper();
        quotes = Arrays.asList(om.readValue(json, Quote[].class));
        return quotes;
    }
    @GetMapping("/status/{id_status}")
    public ResponseEntity<Quote> get() {

        return null;
    }
}
