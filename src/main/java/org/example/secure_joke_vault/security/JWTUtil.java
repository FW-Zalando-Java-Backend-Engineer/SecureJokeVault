package org.example.secure_joke_vault.security;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

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
        Date  now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder() // Start building the JWT using JJWT's fluent builder API
                .setSubject(username)  // Set the "subject" claim — typically the username or user ID
                .setIssuedAt(now)  // Set the "iat" (issued at) claim — when the token was created
                .setExpiration(expiryDate) // Set the "exp" (expiration) claim — when the token will expire
                .signWith( getSigningKey() ,SignatureAlgorithm.HS256) // Sign the token using HMAC SHA-256 and a secret key
                .compact();  // Finalize and serialize the token as a compact string (Base64-encoded JWT)




    }

    private SecretKey getSigningKey() {
        return  Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder() // entry point to create a token parser
                .setSigningKey(getSigningKey()) // use the jwt secret to unlock the token
                .build() // finalizing the parser configuration
                .parseClaimsJwt(token) // Parses the token and validate its signature and expiration
                .getBody() // get the Payload
                .getSubject(); // get the 'sub' claim (username)
    }



}
