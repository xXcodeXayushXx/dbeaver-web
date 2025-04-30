package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;

@Data
public class ConnectionProfileDTO {
    private Long id;
    private Long consumerId;
    private String name;
    private String databaseType;
    private String host;
    private Integer port;
    private String databaseName;
    private String username;
    private String password;
}

