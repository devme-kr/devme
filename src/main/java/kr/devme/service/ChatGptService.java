package kr.devme.service;

import kr.devme.common.exception.ChatGptException;
import kr.devme.dto.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatGptService {

    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.api.url}")
    private String openAiUrl;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.max-tokens}")
    private String maxTokens;

    public String getGptResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        HashMap<String, Object> body  = new HashMap<>();
        body.put("model", model);
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        body.put("max_tokens", Integer.parseInt(maxTokens));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ChatGptResponse chatGptResponse = restTemplate.postForObject(openAiUrl, entity, ChatGptResponse.class);
            if (chatGptResponse != null) {
                return chatGptResponse.getChoices().getFirst().getMessage().getContent();
            } else {
                log.error("Response data does not exist.");
                throw new ChatGptException("Response data does not exist.");
            }
        } catch (HttpClientErrorException e) {
            log.error("Client error occurred: {}", e.getStatusCode(), e);
            throw new ChatGptException("Client error: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("Server error occurred: {}", e.getStatusCode(), e);
            throw new ChatGptException("Server error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            throw new ChatGptException("Server error: " + e.getMessage(), e);
        }
    }

}
