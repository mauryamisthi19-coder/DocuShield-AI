package com.docushield.document.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class AiEntityExtractionService {

    private final RestClient restClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public AiEntityExtractionService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String extractEntities(String extractedText) {

        String prompt = """
                Extract important entities from the following document.

                Return ONLY JSON in this format:

                {
                  "personName":"",
                  "organization":"",
                  "issueDate":"",
                  "emails":[],
                  "phoneNumbers":[],
                  "urls":[]
                }

                Document:
                """ + extractedText;

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.1-8b-instant",
                "messages", new Object[]{
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                },
                "temperature", 0.2
        );

        String response = restClient.post()
                .uri("https://api.groq.com/openai/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(String.class);

        try {

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);

            return root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {

            e.printStackTrace();

            return "Entity Extraction Failed.";

        }
    }
}