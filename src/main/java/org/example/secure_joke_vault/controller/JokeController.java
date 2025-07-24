package org.example.secure_joke_vault.controller;


import lombok.RequiredArgsConstructor;
import org.example.secure_joke_vault.model.Joke;
import org.example.secure_joke_vault.service.JokeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller to expose joke-related endpoints.
 */
@RestController
@RequestMapping("/api/joke")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;


    /**
     * Fetches a random joke from the external API.
     * Only authenticated users can access.
     * @param authentication Spring Security authentication object
     * @return the joke
     */
    @GetMapping
    public ResponseEntity<Joke> getJoke(Authentication authentication) throws IOException{
        String username =  authentication.getName();
        Joke joke = jokeService.fetchAndSaveJoke(username);
        return ResponseEntity.ok(joke);
    }


}
