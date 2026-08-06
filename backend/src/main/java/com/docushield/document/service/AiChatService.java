package com.docushield.document.service;

import com.docushield.document.dto.ChatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class AiChatService {

    private final RestClient restClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public AiChatService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public ChatResponse askQuestion(String documentText, String question) {

        String prompt = """
                You are an AI assistant.

                Answer ONLY from the document below.

                If the answer is not present in the document,
                reply:
                "This information is not available in the document."

                Document:
                """ + documentText + """

                Question:
                """ + question;

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

            String answer = root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return new ChatResponse(answer);

        } catch (Exception e) {
            e.printStackTrace();
            return new ChatResponse("Error while processing AI response.");
        }
    }
}