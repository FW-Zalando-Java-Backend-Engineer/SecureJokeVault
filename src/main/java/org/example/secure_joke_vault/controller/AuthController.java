package org.example.secure_joke_vault.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.secure_joke_vault.dto.SignupRequest;
import org.example.secure_joke_vault.model.Role;
import org.example.secure_joke_vault.model.User;
import org.example.secure_joke_vault.repository.UserRepository;
import org.example.secure_joke_vault.security.JWTUtil;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller to handle sign-up and sign-in logic.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody SignupRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest().body("Username already taken.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(Role.USER)); // Default role

        userRepository.save(user);


    }



}
