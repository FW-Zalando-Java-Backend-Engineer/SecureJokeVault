package org.example.secure_joke_vault.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.secure_joke_vault.model.Joke;
import org.example.secure_joke_vault.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Service responsible for:
 * - Fetching a random joke from external API
 * - Saving the joke to MongoDB with user info
 */
@Service
@RequiredArgsConstructor
public class JokeService {

    private final RestTemplate restTemplate;
    private final JokeRepository jokeRepository;

    @Value("${joke.api.url}")
    private String jokeApiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Joke fetchAndSaveJoke(String username) throws IOException{
        // Call external API
        String response = restTemplate.getForObject(jokeApiUrl, String.class);

        // Parse JSON manually
        JsonNode jsonNode = objectMapper.readTree(response);

        // Map to a Joke object
        Joke joke = new Joke();
        joke.setType(jsonNode.get("type").asText());
        joke.setSetup(jsonNode.get("setup").asText());
        joke.setPunchline(jsonNode.get("punchline").asText());
        joke.setCreatedBy(username);

        // Save to DB
        return jokeRepository.save(joke);
    }


}
