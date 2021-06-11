package com.nssp.aboutnothing.usecase.inbound;

import com.nssp.aboutnothing.data.model.Quote;

import java.util.List;

public interface QuoteList {
    List<Quote> listQuotes(String key);
}
