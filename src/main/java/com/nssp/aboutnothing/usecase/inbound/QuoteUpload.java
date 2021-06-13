package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuoteUpload {
    Quote UploadSingleFile(Quote quotes);
    void UploadMultipleFiles(List<Quote> quotes);
    String uploadMultipleFiles2(List<MultipartFile> multipartFilesList);
}
