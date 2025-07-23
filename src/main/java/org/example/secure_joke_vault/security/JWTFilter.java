package org.example.secure_joke_vault.security;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Intercepts each request to check for JWT token in Authorization header.
 * */
@Component
public class JWTFilter extends OncePerRequestFilter {


}
