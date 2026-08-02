package com.docushield.document.service;

import com.docushield.document.dto.DocumentResponse;
import com.docushield.document.entity.Document;
import com.docushield.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentResponse uploadDocument(MultipartFile file,
                                           Authentication authentication)
            throws IOException {

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

        Document document = Document.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath(filePath.toString())
                .uploadedBy(authentication.getName())
                .status("UPLOADED")
                .build();

        documentRepository.save(document);

        return new DocumentResponse(
                document.getId(),
                document.getFileName(),
                document.getStatus()
        );
    }
}