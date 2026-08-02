package com.docushield.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentResponse {

    private Long id;
    private String fileName;
    private String status;
}