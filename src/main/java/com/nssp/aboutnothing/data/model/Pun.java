package com.nssp.aboutnothing.data.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Pun {
    private String name;
    private MultipartFile file;
}
