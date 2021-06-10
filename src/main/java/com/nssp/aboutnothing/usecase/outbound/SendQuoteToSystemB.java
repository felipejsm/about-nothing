package com.nssp.aboutnothing.usecase.outbound;

import com.nssp.aboutnothing.data.model.Quote;

public interface SendQuoteToSystemB {
    void sendQuote(Quote quote);
}
