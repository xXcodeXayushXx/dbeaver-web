package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.UserDTO;
import com.infoorigin.DBeaverWeb.entities.User;
import com.infoorigin.DBeaverWeb.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        // Check if username already exists
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword()); // Plain text as specified
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Simple password check (plain text)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}