package com.docushield.document.service;

import com.docushield.document.dto.DocumentComparisonResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class AiDocumentComparisonService {

    private final RestClient restClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public AiDocumentComparisonService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public DocumentComparisonResponse compareDocuments(String doc1, String doc2) {

        String prompt = """
                Compare the following two documents.

                Return ONLY JSON in this format:

                {
                  "similarity":"",
                  "differences":"",
                  "recommendation":""
                }

                Document 1:
                """ + doc1 + """

                Document 2:
                """ + doc2;

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

            String aiJson = root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
            System.out.println("========== AI RESPONSE ==========");
            System.out.println(aiJson);
            System.out.println("=================================");

            return mapper.readValue(aiJson, DocumentComparisonResponse.class);

        } catch (Exception e) {

            e.printStackTrace();

            return new DocumentComparisonResponse(
                    "Unknown",
                    "Comparison Failed",
                    "Please try again."
            );

        }

    }

}