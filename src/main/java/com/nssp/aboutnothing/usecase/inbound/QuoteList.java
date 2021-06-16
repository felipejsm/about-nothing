package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuoteList {
    Page<Quote> listQuotes(String key, Pageable pageable);
    Quote uniqueQuote(String key);
}
