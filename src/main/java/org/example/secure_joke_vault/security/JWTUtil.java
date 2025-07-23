package org.example.secure_joke_vault.security;

import io.jsonwebtoken.*;

// import javax.crypto.SecretKey; // Alternative we use java.security.Key interface.
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens
 * */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Generates a JWT token for a given username.
     * @param username the authenticated user's username
     * @return signed JWT token
     */
    public String generateToken(String username){

        long expirationMillis = 24 * 60 * 60 * 1000; // 1 day in milliseconds
        Date  now = new Date(); // time of now
        Date expiryDate = new Date(now.getTime() + expirationMillis); // time of now + 1 day

        return Jwts.builder() // Start building the JWT using JJWT's fluent builder API
                .setSubject(username)  // Set the "subject" claim — typically the username or user ID
                .setIssuedAt(now)  // Set the "iat" (issued at) claim — when the token was created
                .setExpiration(expiryDate) // Set the "exp" (expiration) claim — when the token will expire
                .signWith( getSigningKey() , SignatureAlgorithm.HS256) // Sign the token using HMAC SHA-256 and a secret key
                .compact();  // Finalize and serialize the token as a compact string (Base64-encoded JWT)
    }



    /**
     * Extracts the username from a given token.
     * @param token the JWT token
     * @return the subject (username)
     */
    public String extractUsername(String token){
        return Jwts.parserBuilder() // entry point to parse a token
                .setSigningKey(getSigningKey()) // use the jwt secret to unlock the token
                .build() // finalizing the parser configuration
                .parseClaimsJws(token) // Parses the token and validate its signature and expiration
                .getBody() // get the Payload
                .getSubject(); // get the 'sub' claim (username)
    }


    /**
     * Checks if a token is expired.
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder() // entry point to parse a token parser
                .setSigningKey(getSigningKey()) // use the jwt secret to unlock the token
                .build() // finalizing the parser configuration
                .parseClaimsJws(token) // Parse and validate token
                .getBody() // get the Payload
                .getExpiration(); // Extracts the expiration date
        return  expiration.before(new Date()); // Returns true if expired
    }

    /**
     * Validates the token by checking the username and ensuring it is not expired.
     *
     * @param token    the JWT token to validate
     * @param username the expected username
     * @return true if the token is valid and matches the username
     */
    public boolean isTokenValid(String token, String username){
        String extracted = extractUsername(token);
        return extracted.equals(username) && !isTokenExpired(token);

    }




    /**
     * Converts the JWT secret string into a secure {@link Key}
     * for signing and validating JWTs using HMAC algorithms (e.g., HS256).
     * Requires the secret to be at least 256 bits (32 characters).
     *
     * @return a {@link Key} derived from the configured secret
     */
    private Key getSigningKey() {
        return  Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


}
