//package com.infoorigin.DBeaverWeb.services;
//
//
//import com.infoorigin.DBeaverWeb.entities.User;
//import com.infoorigin.DBeaverWeb.repositories.UserRepository;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
///**
// * Service for managing user-related operations, including authentication and registration.
// */
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    /**
//     * Retrieves the ID of the currently authenticated user.
//     *
//     * @return The user ID.
//     * @throws IllegalStateException if the user is not found.
//     */
//    public Long getCurrentUserId() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
//        return user.getId();
//    }
//
//    /**
//     * Creates a new user with a hashed password.
//     *
//     * @param user The user to create.
//     * @return The saved user.
//     * @throws IllegalArgumentException if the username is already taken or invalid.
//     */
//    public User createUser(User user) {
//        if (user.getUsername() == null || user.getUsername().isEmpty()) {
//            throw new IllegalArgumentException("Username is required");
//        }
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password is required");
//        }
//        if (user.getRole() == null || user.getRole().isEmpty()) {
//            user.setRole("USER"); // Default role
//        }
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//}
