package com.docushield.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentComparisonResponse {

    private String similarity;

    private String differences;

    private String recommendation;

}