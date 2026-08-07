package com.docushield.ai;

import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final GroqService groqService;

    public AiService(GroqService groqService) {
        this.groqService = groqService;
    }
}