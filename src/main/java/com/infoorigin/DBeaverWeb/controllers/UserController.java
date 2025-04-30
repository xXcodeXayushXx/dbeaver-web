package com.infoorigin.DBeaverWeb.controllers;

import com.infoorigin.DBeaverWeb.dtos.LoginRequest;
import com.infoorigin.DBeaverWeb.dtos.UserDTO;
import com.infoorigin.DBeaverWeb.entities.User;
import com.infoorigin.DBeaverWeb.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = userService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(convertToDTO(authenticatedUser));
    }

    private UserDTO convertToDTO(User user) {
        // Convert User entity to UserDTO
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
