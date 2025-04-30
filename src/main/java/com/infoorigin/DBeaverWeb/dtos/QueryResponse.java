package com.infoorigin.DBeaverWeb.dtos;

import lombok.Data;
import java.util.List;

@Data
public class QueryResponse {
    private List<String> columns;
    private List<List<Object>> rows;
    private int rowCount;
    private long executionTimeMs;
}
