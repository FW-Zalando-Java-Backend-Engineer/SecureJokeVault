package org.example.secure_joke_vault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO to return the JWT token after successful login.
 */
@Getter
@Setter
@AllArgsConstructor
public class JWTResponse {
    private String token;
}
