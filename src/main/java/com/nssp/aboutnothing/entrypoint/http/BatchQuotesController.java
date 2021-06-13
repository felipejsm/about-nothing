package com.nssp.aboutnothing.entrypoint.http;

import com.amazonaws.Response;
import com.nssp.aboutnothing.data.model.Quote;
import com.nssp.aboutnothing.usecase.inbound.QuoteUpload;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/batch_quotes")
public class BatchQuotesController {
    private QuoteUpload quoteUpload;
    public BatchQuotesController(QuoteUpload quoteUpload) {
        this.quoteUpload = quoteUpload;
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> post(@RequestParam("files") List<MultipartFile> arquivos) {
        System.out.println(arquivos);
        this.quoteUpload.uploadMultipleFiles2(arquivos);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/status/{id_status}")
    public ResponseEntity<Quote> get() {

        return null;
    }
}
