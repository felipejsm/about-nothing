package com.nssp.aboutnothing.usecase;

import com.nssp.aboutnothing.data.Quote;

import java.util.List;

public interface QuoteUpload {
    public void UploadSingleFile(Quote quotes);
    public void UploadMultipleFiles(List<Quote> quotes);
}
