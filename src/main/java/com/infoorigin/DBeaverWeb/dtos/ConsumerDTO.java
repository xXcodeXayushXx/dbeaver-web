package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;

@Data
public class ConsumerDTO {
    private Long id;
    private String username;
    private String password; // Included in request, excluded in response
    private String email;
}
