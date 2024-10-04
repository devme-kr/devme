package kr.devme.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

class ChatGptServiceTest {

    private ChatGptService chatGptService;

    @BeforeEach
    void setup() {
        //Given
        RestTemplate restTemplate = new RestTemplate();
        chatGptService = new ChatGptService(restTemplate);

        ReflectionTestUtils.setField(chatGptService, "openAiApiKey", "");
        ReflectionTestUtils.setField(chatGptService, "openAiUrl", "https://api.openai.com/v1/chat/completions");
        ReflectionTestUtils.setField(chatGptService, "model", "gpt-4o");
        ReflectionTestUtils.setField(chatGptService, "maxTokens", "2000");
    }

    @Test
    void test() {
        //When
        String response = chatGptService.getGptResponse("안녕");

        //Then
        System.out.println("response = " + response);
        Assertions.assertNotNull(response);
    }
}