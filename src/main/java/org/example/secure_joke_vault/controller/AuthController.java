package org.example.secure_joke_vault.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.secure_joke_vault.dto.JWTResponse;
import org.example.secure_joke_vault.dto.SigninRequest;
import org.example.secure_joke_vault.dto.SignupRequest;
import org.example.secure_joke_vault.model.User;
import org.example.secure_joke_vault.repository.UserRepository;
import org.example.secure_joke_vault.security.JWTUtil;
import org.example.secure_joke_vault.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

        User user = UserMapper.toUser(request, passwordEncoder);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");


    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> login(@Valid @RequestBody SigninRequest request){
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
           // Authentication token = new UsernamePasswordAuthenticationToken("safwan","$5xbaub");
           //  authManager.authenticate(token)
          // PasswordEncoder.matches("$5xbaub", "hashCode in DB");
         // If all is good it return a fully authenticated Authentication object

        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new JWTResponse(token));
    }



}
