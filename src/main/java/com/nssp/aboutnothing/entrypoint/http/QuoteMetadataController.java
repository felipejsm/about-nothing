package com.nssp.aboutnothing.entrypoint.http;

import com.nssp.aboutnothing.data.model.Quote;
import com.nssp.aboutnothing.usecase.inbound.QuoteList;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/quotes_metadata")
public class QuoteMetadataController {
    private QuoteList quoteList;
    public QuoteMetadataController(QuoteList quoteList) {
        this.quoteList = quoteList;
    }
    @GetMapping
    public ResponseEntity<Map<String, Object>> getQuotesMetadata(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {

        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Quote>> getList(@RequestParam String key) {
        return ResponseEntity.ok().body(this.quoteList.listQuotes(key));
    }

    @GetMapping("/page")
    public Page<Quote> get(Pageable pageable) {
        return null;//return this.repository.findAll(pageable);
    }

}
