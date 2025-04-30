package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;

@Data
public class QueryRequest {
    private String connectionId;
    private String query;
}
