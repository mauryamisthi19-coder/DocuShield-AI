package com.docushield.document.controller;

import com.docushield.document.dto.ChatRequest;
import com.docushield.document.dto.ChatResponse;
import com.docushield.document.entity.Document;
import com.docushield.document.repository.DocumentRepository;
import com.docushield.document.service.AiChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final DocumentRepository documentRepository;
    private final AiChatService aiChatService;

    public ChatController(DocumentRepository documentRepository,
                          AiChatService aiChatService) {
        this.documentRepository = documentRepository;
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat/{id}")
    public ChatResponse chat(@PathVariable Long id,
                             @RequestBody ChatRequest request) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return aiChatService.askQuestion(
                document.getExtractedText(),
                request.getQuestion()
        );
    }
}