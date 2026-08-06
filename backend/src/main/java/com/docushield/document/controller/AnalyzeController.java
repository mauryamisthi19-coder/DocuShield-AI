package com.docushield.document.controller;

import com.docushield.document.dto.DocumentAnalysisResponse;
import com.docushield.document.dto.DocumentComparisonResponse;
import com.docushield.document.entity.Document;
import com.docushield.document.repository.DocumentRepository;
import com.docushield.document.service.AiDocumentAnalyzerService;
import com.docushield.document.service.AiDocumentComparisonService;
import com.docushield.document.service.AiEntityExtractionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AnalyzeController {

    private final DocumentRepository documentRepository;
    private final AiDocumentAnalyzerService analyzerService;
    private final AiEntityExtractionService aiEntityExtractionService;
    private final AiDocumentComparisonService aiDocumentComparisonService;

    public AnalyzeController(DocumentRepository documentRepository,
                             AiDocumentAnalyzerService analyzerService,
                             AiEntityExtractionService aiEntityExtractionService,
                             AiDocumentComparisonService aiDocumentComparisonService) {

        this.documentRepository = documentRepository;
        this.analyzerService = analyzerService;
        this.aiEntityExtractionService = aiEntityExtractionService;
        this.aiDocumentComparisonService = aiDocumentComparisonService;
    }

    @PostMapping("/analyze/{id}")
    public DocumentAnalysisResponse analyze(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return analyzerService.analyzeDocument(
                document.getExtractedText()
        );
    }

    @GetMapping("/entities/{id}")
    public String extractEntities(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return aiEntityExtractionService.extractEntities(
                document.getExtractedText()
        );
    }

    @PostMapping("/compare/{id1}/{id2}")
    public DocumentComparisonResponse compareDocuments(
            @PathVariable Long id1,
            @PathVariable Long id2) {

        Document document1 = documentRepository.findById(id1)
                .orElseThrow(() -> new RuntimeException("First document not found"));

        Document document2 = documentRepository.findById(id2)
                .orElseThrow(() -> new RuntimeException("Second document not found"));

        return aiDocumentComparisonService.compareDocuments(
                document1.getExtractedText(),
                document2.getExtractedText()
        );
    }
}