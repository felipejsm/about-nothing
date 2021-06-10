package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;

import java.util.List;

public interface QuoteDelete {
    void deleteSingleFile(Quote quote);
    void deleteMultipleFiles(List<Quote> quotes);
}
