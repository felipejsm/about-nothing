package com.nssp.aboutnothing.data.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Quote {
    private String id;
    private String name;
    private String contentType;
    private String description;
    private MultipartFile physical;
}
