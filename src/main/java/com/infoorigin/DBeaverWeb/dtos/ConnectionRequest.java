package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;

@Data
public class ConnectionRequest {
    private Long profileId; // Optional, can connect using saved profile
    private String dbType;
    private String host;
    private Integer port;
    private String databaseName;
    private String username;
    private String password;
}
