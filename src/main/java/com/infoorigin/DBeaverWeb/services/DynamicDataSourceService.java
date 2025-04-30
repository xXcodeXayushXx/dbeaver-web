//package com.infoorigin.DBeaverWeb.services;
//import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class DynamicDataSourceService {
//
//    // Map of connection ID to DataSource
//    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
//
//    public String createDataSource(String host, String port, String database, String username, String password, String driverClassName) {
//        validateConnectionDetails(host, port, database, username, driverClassName);
//
//        String jdbcUrl = buildJdbcUrl(host, port, database, driverClassName);
//        HikariDataSource dataSource = createHikariDataSource(jdbcUrl, username, password, driverClassName);
//
//        String connectionId = UUID.randomUUID().toString();
//        dataSources.put(connectionId, dataSource);
//
//        return connectionId;
//    }
//
//    public String createDataSourceFromProfile(ConnectionProfile profile) {
//        if (profile == null) {
//            throw new IllegalArgumentException("Connection profile cannot be null");
//        }
//        return createDataSource(
//                profile.getHost(),
//                profile.getPort(),
//                profile.getDatabase(),
//                profile.getUsername(),
//                profile.getPassword(),
//                profile.getDriverClassName()
//        );
//    }
//
//    public DataSource getDataSource(String connectionId) {
//        validateConnectionId(connectionId);
//        DataSource dataSource = dataSources.get(connectionId);
//        if (dataSource == null) {
//            throw new IllegalArgumentException("Connection ID not found: " + connectionId);
//        }
//        return dataSource;
//    }
//
//    public void removeDataSource(String connectionId) {
//        validateConnectionId(connectionId);
//        DataSource dataSource = dataSources.remove(connectionId);
//        if (dataSource == null) {
//            throw new IllegalArgumentException("Connection ID not found: " + connectionId);
//        }
//        if (dataSource instanceof HikariDataSource hikariDataSource) {
//            hikariDataSource.close();
//        }
//    }
//
//    private void validateConnectionDetails(String host, String port, String database, String username, String driverClassName) {
//        if (host == null || host.isEmpty()) {
//            throw new IllegalArgumentException("Host is required");
//        }
//        if (port == null || port.isEmpty()) {
//            throw new IllegalArgumentException("Port is required");
//        }
//        if (database == null || database.isEmpty()) {
//            throw new IllegalArgumentException("Database name is required");
//        }
//        if (username == null || username.isEmpty()) {
//            throw new IllegalArgumentException("Username is required");
//        }
//        if (driverClassName == null || driverClassName.isEmpty()) {
//            throw new IllegalArgumentException("Driver class name is required");
//        }
//    }
//
//    private void validateConnectionId(String connectionId) {
//        if (connectionId == null || connectionId.isEmpty()) {
//            throw new IllegalArgumentException("Connection ID is required");
//        }
//    }
//
//    private String buildJdbcUrl(String host, String port, String database, String driverClassName) {
//        if ("org.postgresql.Driver".equals(driverClassName)) {
//            return String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
//        } else if ("org.h2.Driver".equals(driverClassName)) {
//            return String.format("jdbc:h2:tcp://%s:%s/%s", host, port, database);
//        } else {
//            throw new IllegalArgumentException("Unsupported driver class: " + driverClassName);
//        }
//    }
//
//    private HikariDataSource createHikariDataSource(String jdbcUrl, String username, String password, String driverClassName) {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl(jdbcUrl);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password != null ? password : "");
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setMaximumPoolSize(10);
//        dataSource.setMinimumIdle(2);
//        dataSource.setIdleTimeout(300000); // 5 minutes
//        dataSource.setConnectionTimeout(30000); // 30 seconds
//        return dataSource;
//    }
//}