package com.project.pfe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor @Data @ToString
public class Response {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
