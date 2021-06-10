package com.nssp.aboutnothing.data.model;

import org.springframework.web.multipart.MultipartFile;

public class Quote {
    public String eTag;
    public String name;
    public String contentType;
    public byte[] physical;
    public String description;
    public MultipartFile mult;
}
