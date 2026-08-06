package com.docushield.document.controller;

import com.docushield.document.entity.Document;
import com.docushield.document.repository.DocumentRepository;
import com.docushield.document.service.AiSummaryService;
import org.springframework.web.bind.annotation.*;
import com.docushield.document.service.AiSensitiveDataService;
@RestController
@RequestMapping("/api/ai")
public class SummaryController {

    private final DocumentRepository documentRepository;
    private final AiSummaryService aiSummaryService;
    private final AiSensitiveDataService aiSensitiveDataService;

    public SummaryController(DocumentRepository documentRepository,
                             AiSummaryService aiSummaryService,
                             AiSensitiveDataService aiSensitiveDataService) {

        this.documentRepository = documentRepository;
        this.aiSummaryService = aiSummaryService;
        this.aiSensitiveDataService = aiSensitiveDataService;
    }


    @GetMapping("/summary/{id}")
    public String generateSummary(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return aiSummaryService.generateSummary(document.getExtractedText());
    }

    @GetMapping("/classify/{id}")
    public String classifyDocument(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return aiSummaryService.classifyDocument(document.getExtractedText());
    }
    @GetMapping("/sensitive/{id}")
    public String detectSensitiveData(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return aiSensitiveDataService.detectSensitiveData(document.getExtractedText());
    }
}
