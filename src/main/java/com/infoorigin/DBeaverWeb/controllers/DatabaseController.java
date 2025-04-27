package com.infoorigin.DBeaverWeb.controllers;

import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import com.infoorigin.DBeaverWeb.repositories.ConnectionProfileRepository;
import com.infoorigin.DBeaverWeb.services.DynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for managing connection profiles, database connections, and query execution.
 */
@RestController
@RequestMapping("/api")
public class DatabaseController {

    @Autowired
    private DynamicDataSourceService dataSourceService;

    @Autowired
    private ConnectionProfileRepository connectionProfileRepository;

    // Save a connection profile
    @PostMapping("/profiles")
    public ResponseEntity<?> saveConnectionProfile(@RequestBody ConnectionProfileRequest request) {
        try {
            validateProfileRequest(request);
            ConnectionProfile profile = new ConnectionProfile();
            profile.setName(request.name());
            profile.setHost(request.host());
            profile.setPort(request.port());
            profile.setDatabase(request.database());
            profile.setUsername(request.username());
            profile.setPassword(request.password());
            profile.setDriverClassName(request.driverClassName());

            ConnectionProfile savedProfile = connectionProfileRepository.save(profile);
            return ResponseEntity.ok(Map.of("id", savedProfile.getId(), "status", "saved"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to save profile: " + e.getMessage()));
        }
    }

    // List all connection profiles
    @GetMapping("/profiles")
    public ResponseEntity<?> listConnectionProfiles() {
        try {
            Iterable<ConnectionProfile> profiles = connectionProfileRepository.findAll();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to list profiles: " + e.getMessage()));
        }
    }

    // Delete a connection profile
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<?> deleteConnectionProfile(@PathVariable Long id) {
        try {
            Optional<ConnectionProfile> profile = connectionProfileRepository.findById(id);
            if (profile.isEmpty()) {
                return ResponseEntity.status(400).body(Map.of("error", "Profile not found: " + id));
            }
            connectionProfileRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("status", "deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete profile: " + e.getMessage()));
        }
    }

    // Connect using a saved profile
    @PostMapping("/connect/profile/{id}")
    public ResponseEntity<?> connectUsingProfile(@PathVariable Long id) {
        try {
            Optional<ConnectionProfile> profile = connectionProfileRepository.findById(id);
            if (profile.isEmpty()) {
                return ResponseEntity.status(400).body(Map.of("error", "Profile not found: " + id));
            }
            String connectionId = dataSourceService.createDataSourceFromProfile(profile.get());
            return ResponseEntity.ok(Map.of("connectionId", connectionId, "status", "success"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Connection failed: " + e.getMessage()));
        }
    }

    // Connect manually
    @PostMapping("/connect")
    public ResponseEntity<?> connect(@RequestBody ConnectionRequest request) {
        try {
            String connectionId = dataSourceService.createDataSource(
                    request.host(),
                    request.port(),
                    request.database(),
                    request.username(),
                    request.password(),
                    request.driverClassName()
            );
            return ResponseEntity.ok(Map.of("connectionId", connectionId, "status", "success"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Connection failed: " + e.getMessage()));
        }
    }

    // Execute a query
    @PostMapping("/query")
    public ResponseEntity<?> executeQuery(@RequestBody QueryRequest request) {
        try {
            DataSource dataSource = dataSourceService.getDataSource(request.connectionId());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<Map<String, Object>> results = jdbcTemplate.queryForList(request.query());
            List<String> columns = results.isEmpty() ? List.of() : results.get(0).keySet().stream().toList();
            return ResponseEntity.ok(Map.of("results", results, "columns", columns, "error", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Query failed: " + e.getMessage()));
        }
    }

    // Disconnect
    @PostMapping("/disconnect")
    public ResponseEntity<?> disconnect(@RequestBody DisconnectRequest request) {
        try {
            dataSourceService.removeDataSource(request.connectionId());
            return ResponseEntity.ok(Map.of("status", "disconnected"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Disconnection failed: " + e.getMessage()));
        }
    }

    private void validateProfileRequest(ConnectionProfileRequest request) {
        if (request.name() == null || request.name().isEmpty()) {
            throw new IllegalArgumentException("Profile name is required");
        }
        if (request.host() == null || request.host().isEmpty()) {
            throw new IllegalArgumentException("Host is required");
        }
        if (request.port() == null || request.port().isEmpty()) {
            throw new IllegalArgumentException("Port is required");
        }
        if (request.database() == null || request.database().isEmpty()) {
            throw new IllegalArgumentException("Database name is required");
        }
        if (request.username() == null || request.username().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (request.driverClassName() == null || request.driverClassName().isEmpty()) {
            throw new IllegalArgumentException("Driver class name is required");
        }
    }
}

record ConnectionProfileRequest(String name, String host, String port, String database, String username, String password, String driverClassName) {}
record ConnectionRequest(String host, String port, String database, String username, String password, String driverClassName) {}
record QueryRequest(String connectionId, String query) {}
record DisconnectRequest(String connectionId) {}