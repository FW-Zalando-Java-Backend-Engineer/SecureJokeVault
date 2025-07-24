package org.example.secure_joke_vault.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.secure_joke_vault.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Intercepts each request to check for JWT token in Authorization header.
 * */
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(    HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain )
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        // authHeader is formated : Authorization: "Bearer <JWT - TOKEN>"

        // Check if Token is Present and Well-Formed
        // Ensure the header is not null and follows the correct prefix "Bearer ".
        if(authHeader != null &&  authHeader.startsWith("Bearer ")){
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                   // Load User Details from our DB
                    var userDetails = userDetailsService.loadUserByUsername(username);

                    // Validate the Token
                    if(jwtUtil.isTokenValid(token, userDetails.getUsername())){
                        // Create a Spring Security authentication object.
                        var authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                       // Set in Security Context
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }

                }
        }

        // Continue Filter Chain
        filterChain.doFilter(request, response);

    }
}
