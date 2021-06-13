package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;

import java.util.List;

public interface QuoteUpload {
    Quote UploadSingleFile(Quote quotes);
    public void UploadMultipleFiles(List<Quote> quotes);
}
