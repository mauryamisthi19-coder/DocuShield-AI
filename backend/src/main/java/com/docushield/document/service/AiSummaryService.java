package com.docushield.document.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class AiSummaryService {

    private final RestClient restClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public AiSummaryService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String generateSummary(String extractedText) {

        String prompt = """
                You are an AI document analyzer.

                Read the following document text and generate a concise summary.

                Instructions:
                - Summarize in 4-6 bullet points.
                - Mention the person's name if present.
                - Mention the document title.
                - Mention the course/certificate/topic.
                - If the document is short, still summarize the available information.

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
                "temperature", 0.3
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
            return "Error while parsing AI response.";
        }
    }
    public String classifyDocument(String extractedText) {

        String prompt = """
            You are an AI document classifier.

            Analyze the following document and answer ONLY in this format:

            Document Type:
            Confidence:
            Reason:

            Possible document types:
            - Resume
            - Certificate
            - Invoice
            - ID Card
            - Marksheet
            - Report
            - Other

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
            return "Error while classifying document.";
        }
    }
}