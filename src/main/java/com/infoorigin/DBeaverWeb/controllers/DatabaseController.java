package com.infoorigin.DBeaverWeb.controllers;

import com.infoorigin.DBeaverWeb.dtos.*;
import com.infoorigin.DBeaverWeb.services.DatabaseConnectionService;
import com.infoorigin.DBeaverWeb.services.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    private final DatabaseConnectionService connectionService;
    private final QueryService queryService;

    public DatabaseController(
            DatabaseConnectionService connectionService,
            QueryService queryService) {
        this.connectionService = connectionService;
        this.queryService = queryService;
    }

    @PostMapping("/connect")
    public ResponseEntity<ConnectionResponse> connect(
            @RequestBody ConnectionRequest connectionRequest) {
        String connectionId = connectionService.connect(connectionRequest);

        ConnectionResponse response = new ConnectionResponse();
        response.setConnectionId(connectionId);
        response.setMessage("Connected successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/disconnect")
    public ResponseEntity<Map<String, String>> disconnect(
            @RequestBody DisconnectionRequest request) {
        connectionService.disconnect(request.getConnectionId());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Disconnected successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/query")
    public ResponseEntity<QueryResponse> executeQuery(
            @RequestBody QueryRequest queryRequest) {
        QueryResult result = queryService.executeQuery(
                queryRequest.getConnectionId(),
                queryRequest.getQuery()
        );

        QueryResponse response = new QueryResponse();
        response.setColumns(result.getColumns());
        response.setRows(result.getRows());
        response.setRowCount(result.getRowCount());
        response.setExecutionTimeMs(result.getExecutionTimeMs());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/databases")
    public ResponseEntity<List<String>> getDatabases(
            @RequestParam String connectionId) {
        List<String> databases = queryService.getDatabases(connectionId);
        return ResponseEntity.ok(databases);
    }

    @GetMapping("/tables")
    public ResponseEntity<List<String>> getTables(
            @RequestParam String connectionId,
            @RequestParam String database) {
        List<String> tables = queryService.getTables(connectionId, database);
        return ResponseEntity.ok(tables);
    }
}