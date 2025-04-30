package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.ConnectionProfileDTO;
import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import com.infoorigin.DBeaverWeb.entities.User;
import com.infoorigin.DBeaverWeb.repositories.ConnectionProfileRepository;
import com.infoorigin.DBeaverWeb.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConnectionProfileService {

    private final ConnectionProfileRepository connectionProfileRepository;
    private final UserRepository userRepository;

    public ConnectionProfileService(
            ConnectionProfileRepository connectionProfileRepository,
            UserRepository userRepository) {
        this.connectionProfileRepository = connectionProfileRepository;
        this.userRepository = userRepository;
    }

    public ConnectionProfile createConnectionProfile(ConnectionProfileDTO profileDTO) {
        // Find user
        User user = userRepository.findById(profileDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create connection profile
        ConnectionProfile profile = new ConnectionProfile();
        profile.setUser(user);
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

    public List<ConnectionProfile> getConnectionProfilesByUserId(Long userId) {
        return connectionProfileRepository.findByUserId(userId);
    }

    public void updateLastConnected(Long profileId) {
        ConnectionProfile profile = getConnectionProfileById(profileId);
        profile.setLastConnectedAt(LocalDateTime.now());
        connectionProfileRepository.save(profile);
    }
}