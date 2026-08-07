package com.docushield.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final RestClient.Builder restClientBuilder;

    public GroqService(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }
}