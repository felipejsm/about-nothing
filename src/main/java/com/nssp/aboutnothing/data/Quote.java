package com.nssp.aboutnothing.data;

import org.springframework.util.MimeType;

import java.io.File;
import java.util.UUID;

public class Quote {
    public UUID uuid;
    public String name;
    public String contentType;
    public File physical;
    public String description;
}
