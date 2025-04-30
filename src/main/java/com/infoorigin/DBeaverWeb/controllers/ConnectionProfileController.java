package com.infoorigin.DBeaverWeb.controllers;

import com.infoorigin.DBeaverWeb.dtos.ConnectionProfileDTO;
import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import com.infoorigin.DBeaverWeb.services.ConnectionProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/connections")
public class ConnectionProfileController {

    private final ConnectionProfileService connectionProfileService;

    public ConnectionProfileController(ConnectionProfileService connectionProfileService) {
        this.connectionProfileService = connectionProfileService;
    }

    @PostMapping
    public ResponseEntity<ConnectionProfileDTO> createConnectionProfile(
            @RequestBody ConnectionProfileDTO profileDTO) {
        ConnectionProfile profile = connectionProfileService.createConnectionProfile(profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToDTO(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionProfileDTO> getConnectionProfile(@PathVariable Long id) {
        ConnectionProfile profile = connectionProfileService.getConnectionProfileById(id);
        return ResponseEntity.ok(convertToDTO(profile));
    }

    @GetMapping("/consumer/{consumerId}")
    public ResponseEntity<List<ConnectionProfileDTO>> getConnectionProfilesByConsumer(
            @PathVariable Long consumerId) {
        List<ConnectionProfile> profiles =
                connectionProfileService.getConnectionProfilesByConsumerId(consumerId);
        List<ConnectionProfileDTO> profileDTOs = profiles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(profileDTOs);
    }

    private ConnectionProfileDTO convertToDTO(ConnectionProfile profile) {
        // Convert ConnectionProfile entity to ConnectionProfileDTO
        ConnectionProfileDTO dto = new ConnectionProfileDTO();
        dto.setId(profile.getId());
        dto.setConsumerId(profile.getConsumer().getId());
        dto.setName(profile.getName());
        dto.setDatabaseType(profile.getDatabaseType());
        dto.setHost(profile.getHost());
        dto.setPort(profile.getPort());
        dto.setDatabaseName(profile.getDatabaseName());
        dto.setUsername(profile.getUsername());
        return dto;
    }
}
