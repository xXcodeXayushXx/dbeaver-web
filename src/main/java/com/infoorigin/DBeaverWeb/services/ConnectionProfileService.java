package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.ConnectionProfileDTO;
import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import com.infoorigin.DBeaverWeb.entities.Consumer;
import com.infoorigin.DBeaverWeb.repositories.ConnectionProfileRepository;
import com.infoorigin.DBeaverWeb.repositories.ConsumerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConnectionProfileService {

    private final ConnectionProfileRepository connectionProfileRepository;
    private final ConsumerRepository consumerRepository;

    public ConnectionProfileService(
            ConnectionProfileRepository connectionProfileRepository,
            ConsumerRepository consumerRepository) {
        this.connectionProfileRepository = connectionProfileRepository;
        this.consumerRepository = consumerRepository;
    }

    public ConnectionProfile createConnectionProfile(ConnectionProfileDTO profileDTO) {
        // Find consumer
        Consumer consumer = consumerRepository.findById(profileDTO.getConsumerId())
                .orElseThrow(() -> new RuntimeException("Consumer not found"));

        // Create connection profile
        ConnectionProfile profile = new ConnectionProfile();
        profile.setConsumer(consumer);
        profile.setName(profileDTO.getName());
        profile.setDatabaseType(profileDTO.getDatabaseType());
        profile.setHost(profileDTO.getHost());
        profile.setPort(profileDTO.getPort());
        profile.setDatabaseName(profileDTO.getDatabaseName());
        profile.setUsername(profileDTO.getUsername());
        profile.setPassword(profileDTO.getPassword());
        profile.setCreatedAt(LocalDateTime.now());

        return connectionProfileRepository.save(profile);
    }

    public ConnectionProfile getConnectionProfileById(Long id) {
        return connectionProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Connection profile not found"));
    }

    public List<ConnectionProfile> getConnectionProfilesByConsumerId(Long consumerId) {
        return connectionProfileRepository.findByConsumerId(consumerId);
    }

    public void updateLastConnected(Long profileId) {
        ConnectionProfile profile = getConnectionProfileById(profileId);
        profile.setLastConnectedAt(LocalDateTime.now());
        connectionProfileRepository.save(profile);
    }
}