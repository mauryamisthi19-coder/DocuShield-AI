package com.docushield.document.controller;

import com.docushield.document.dto.DocumentResponse;
import com.docushield.document.service.DocumentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public DocumentResponse uploadDocument(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {

        return documentService.uploadDocument(file, authentication);
    }
    @GetMapping("/test")
    public String test(Authentication authentication) {

        if (authentication == null) {
            return "Authentication is NULL";
        }

        return "Logged in as: " + authentication.getName();
    }
}