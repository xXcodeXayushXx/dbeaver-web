package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.ConsumerDTO;
import com.infoorigin.DBeaverWeb.entities.Consumer;
import com.infoorigin.DBeaverWeb.repositories.ConsumerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public Consumer createConsumer(ConsumerDTO consumerDTO) {
        // Check if consumername already exists
        if (consumerRepository.findByUsername(consumerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new consumer
        Consumer consumer = new Consumer();
        consumer.setUsername(consumerDTO.getUsername());
        consumer.setPassword(consumerDTO.getPassword()); // Plain text as specified
        consumer.setEmail(consumerDTO.getEmail());
        consumer.setCreatedAt(LocalDateTime.now());

        return consumerRepository.save(consumer);
    }

    public Consumer getConsumerById(Long id) {
        return consumerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));
    }

    public Consumer authenticateConsumer(String consumername, String password) {
        Consumer consumer = consumerRepository.findByUsername(consumername)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Simple password check (plain text)
        if (!consumer.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        return consumer;
    }
}