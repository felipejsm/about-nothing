package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;

import java.util.List;

public interface QuoteDelete {
    void deleteSingleFile(String key);
    void deleteMultipleFiles(List<Quote> quotes);
}
