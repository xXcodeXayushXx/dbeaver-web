package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.ConnectionRequest;
import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DatabaseConnectionService {

    private final ConnectionProfileService connectionProfileService;
    private final Map<String, DatabaseConnectionInfo> activeConnections = new ConcurrentHashMap<>();

    public DatabaseConnectionService(ConnectionProfileService connectionProfileService) {
        this.connectionProfileService = connectionProfileService;
    }

    public String connect(ConnectionRequest request) {
        Connection connection = null;
        String url;
        String username;
        String password;
        Long profileId = null;

        try {
            // If connecting using saved profile
            if (request.getProfileId() != null) {
                ConnectionProfile profile = connectionProfileService.getConnectionProfileById(request.getProfileId());
                profileId = profile.getId();

                url = buildJdbcUrl(
                        profile.getDatabaseType(),
                        profile.getHost(),
                        profile.getPort(),
                        profile.getDatabaseName()
                );
                username = profile.getUsername();
                password = profile.getPassword();
            }
            // If connecting using direct credentials
            else {
                url = buildJdbcUrl(
                        request.getDatabaseType(),
                        request.getHost(),
                        request.getPort(),
                        request.getDatabaseName()
                );
                username = request.getUsername();
                password = request.getPassword();
            }

            // Establish connection using DriverManager
            connection = DriverManager.getConnection(url, username, password);

            // Generate connection ID
            String connectionId = UUID.randomUUID().toString();

            // Store connection in memory
            DatabaseConnectionInfo connectionInfo = new DatabaseConnectionInfo();
            connectionInfo.setConnection(connection);
            connectionInfo.setProfileId(profileId);
            connectionInfo.setDatabaseType(request.getDatabaseType() != null ? request.getDatabaseType() :
                    connectionProfileService.getConnectionProfileById(profileId).getDatabaseType());
            connectionInfo.setConnectedAt(LocalDateTime.now());

            activeConnections.put(connectionId, connectionInfo);

            // Update last connected timestamp if using profile
            if (profileId != null) {
                connectionProfileService.updateLastConnected(profileId);
            }

            return connectionId;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    // Log error
                }
            }
            throw new RuntimeException("Failed to connect to database: " + e.getMessage());
        }
    }

    public void disconnect(String connectionId) {
        DatabaseConnectionInfo connectionInfo = activeConnections.get(connectionId);
        if (connectionInfo == null) {
            throw new RuntimeException("Connection not found or already closed");
        }

        try {
            connectionInfo.getConnection().close();
            activeConnections.remove(connectionId);
        } catch (SQLException e) {
            throw new RuntimeException("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection(String connectionId) {
        DatabaseConnectionInfo connectionInfo = activeConnections.get(connectionId);
        if (connectionInfo == null) {
            throw new RuntimeException("No active connection found with ID: " + connectionId);
        }
        return connectionInfo.getConnection();
    }

    public boolean isConnected(String connectionId) {
        return activeConnections.containsKey(connectionId);
    }

    private String buildJdbcUrl(String databaseType, String host, Integer port, String databaseName) {
        switch (databaseType.toLowerCase()) {
            case "mysql":
                return "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            case "postgresql":
                return "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
            case "sqlserver":
                return "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + databaseName;
            case "oracle":
                return "jdbc:oracle:thin:@" + host + ":" + port + ":" + databaseName;
            default:
                throw new RuntimeException("Unsupported database type: " + databaseType);
        }
    }

    // Helper class to store connection and metadata
    private static class DatabaseConnectionInfo {
        private Connection connection;
        private Long profileId;
        private String databaseType;
        private LocalDateTime connectedAt;

        // Getters and setters
        public Connection getConnection() { return connection; }
        public void setConnection(Connection connection) { this.connection = connection; }
        public Long getProfileId() { return profileId; }
        public void setProfileId(Long profileId) { this.profileId = profileId; }
        public String getDatabaseType() { return databaseType; }
        public void setDatabaseType(String databaseType) { this.databaseType = databaseType; }
        public LocalDateTime getConnectedAt() { return connectedAt; }
        public void setConnectedAt(LocalDateTime connectedAt) { this.connectedAt = connectedAt; }
    }
}
