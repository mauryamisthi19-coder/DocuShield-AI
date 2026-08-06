package com.docushield.document.service;

import com.docushield.document.dto.DocumentAnalysisResponse;
import org.springframework.stereotype.Service;

@Service
public class AiDocumentAnalyzerService {

    private final AiSummaryService aiSummaryService;
    private final AiSensitiveDataService aiSensitiveDataService;

    public AiDocumentAnalyzerService(
            AiSummaryService aiSummaryService,
            AiSensitiveDataService aiSensitiveDataService) {

        this.aiSummaryService = aiSummaryService;
        this.aiSensitiveDataService = aiSensitiveDataService;
    }

    public DocumentAnalysisResponse analyzeDocument(String extractedText) {

        // Existing Services
        String summary = aiSummaryService.generateSummary(extractedText);

        String classification = aiSummaryService.classifyDocument(extractedText);

        String sensitive = aiSensitiveDataService.detectSensitiveData(extractedText);

        // Recommendation
        String recommendation;

        if (classification.toLowerCase().contains("aadhaar")) {

            recommendation =
                    "This document contains highly sensitive personal information. Do not share it publicly.";

        } else if (classification.toLowerCase().contains("pan")) {

            recommendation =
                    "PAN Card contains confidential information. Share only with trusted organizations.";

        } else if (classification.toLowerCase().contains("bank")) {

            recommendation =
                    "Bank statements should be shared carefully because they contain financial information.";

        } else if (classification.toLowerCase().contains("medical")) {

            recommendation =
                    "Medical reports contain sensitive health information.";

        } else {

            recommendation =
                    "No special precautions detected.";
        }

        return new DocumentAnalysisResponse(

                classification,

                summary,

                sensitive,

                recommendation

        );
    }
}