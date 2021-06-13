package com.nssp.aboutnothing.entrypoint.http;

import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/batch_quotes")
public class BatchQuotesController {
    @PostMapping
    public ResponseEntity<List<Quote>> post() {

        return null;
    }
    @GetMapping("/status/{id_status}")
    public ResponseEntity<Quote> get() {
        return null;
    }
}
