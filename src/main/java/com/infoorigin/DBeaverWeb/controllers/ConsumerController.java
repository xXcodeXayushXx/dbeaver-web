package com.infoorigin.DBeaverWeb.controllers;

import com.infoorigin.DBeaverWeb.dtos.LoginRequest;
import com.infoorigin.DBeaverWeb.dtos.ConsumerDTO;
import com.infoorigin.DBeaverWeb.entities.Consumer;
import com.infoorigin.DBeaverWeb.services.ConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping
    public ResponseEntity<ConsumerDTO> createConsumer(@RequestBody ConsumerDTO consumerDTO) {
        Consumer createdConsumer = consumerService.createConsumer(consumerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdConsumer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumerDTO> getConsumerById(@PathVariable Long id) {
        Consumer consumer = consumerService.getConsumerById(id);
        return ResponseEntity.ok(convertToDTO(consumer));
    }

    @PostMapping("/login")
    public ResponseEntity<ConsumerDTO> login(@RequestBody LoginRequest loginRequest) {
        Consumer authenticatedConsumer = consumerService.authenticateConsumer(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(convertToDTO(authenticatedConsumer));
    }

    private ConsumerDTO convertToDTO(Consumer consumer) {
        // Convert Consumer entity to ConsumerDTO
        ConsumerDTO dto = new ConsumerDTO();
        dto.setId(consumer.getId());
        dto.setUsername(consumer.getUsername());
        dto.setEmail(consumer.getEmail());
        return dto;
    }
}
