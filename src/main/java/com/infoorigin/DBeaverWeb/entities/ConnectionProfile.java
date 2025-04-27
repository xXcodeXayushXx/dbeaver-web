package com.infoorigin.DBeaverWeb.entities;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("connection_profiles")
public class ConnectionProfile {
    @Id
    private Long id;
    private String name; // User-defined name for the profile (e.g., "Production DB")
    private String host; // Database host (e.g., "localhost")
    private String port; // Database port (e.g., "5432")
    private String database; // Database name (e.g., "mydb")
    private String username; // Database username
    private String password; // Plaintext password (consider encryption in production)
    private String driverClassName; // JDBC driver (e.g., "org.postgresql.Driver")
}