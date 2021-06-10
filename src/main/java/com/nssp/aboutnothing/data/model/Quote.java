package com.nssp.aboutnothing.data.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class Quote {
    public UUID uuid;
    public String name;
    public String contentType;
    public byte[] physical;
    public String description;
    public MultipartFile mult;
}
