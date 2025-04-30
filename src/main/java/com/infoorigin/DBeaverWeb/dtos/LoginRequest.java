package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}