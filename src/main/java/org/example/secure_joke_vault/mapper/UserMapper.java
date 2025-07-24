package org.example.secure_joke_vault.mapper;

import org.example.secure_joke_vault.dto.SignupRequest;
import org.example.secure_joke_vault.model.Role;
import org.example.secure_joke_vault.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapper {

    public static User toUser(SignupRequest request, PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(List.of(Role.USER)); // Default role
        return user;
    }
}
