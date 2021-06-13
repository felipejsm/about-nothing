package com.nssp.aboutnothing.entrypoint.http;

import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/batch_quotes")
public class BatchQuotesController {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Quote> post(@RequestParam("files") List<MultipartFile> arquivos) {
        System.out.println(arquivos);
        return null;
    }
    @GetMapping("/status/{id_status}")
    public ResponseEntity<Quote> get() {

        return null;
    }
}
