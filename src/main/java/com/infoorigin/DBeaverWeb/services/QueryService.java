package com.infoorigin.DBeaverWeb.services;

import com.infoorigin.DBeaverWeb.dtos.QueryResult;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {

    private final DatabaseConnectionService connectionService;

    public QueryService(DatabaseConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public QueryResult executeQuery(String connectionId, String query) {
        Connection connection = connectionService.getConnection(connectionId);
        QueryResult result = new QueryResult();

        long startTime = System.currentTimeMillis();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Get metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Extract column names
            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(metaData.getColumnName(i));
            }
            result.setColumns(columns);

            // Extract rows
            List<List<Object>> rows = new ArrayList<>();
            int rowCount = 0;

            while (rs.next()) {
                rowCount++;
                List<Object> row = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    // Get the value based on the column type
                    Object value = rs.getObject(i);
                    row.add(value);
                }

                rows.add(row);
            }

            result.setRows(rows);
            result.setRowCount(rowCount);

        } catch (SQLException e) {
            throw new RuntimeException("Error executing query: " + e.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            result.setExecutionTimeMs(endTime - startTime);
        }

        return result;
    }

    public List<String> getDatabases(String connectionId) {
        Connection connection = connectionService.getConnection(connectionId);
        List<String> databases = new ArrayList<>();

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getCatalogs();

            while (rs.next()) {
                databases.add(rs.getString("TABLE_CAT"));
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving databases: " + e.getMessage());
        }

        return databases;
    }

    public List<String> getTables(String connectionId, String database) {
        Connection connection = connectionService.getConnection(connectionId);
        List<String> tables = new ArrayList<>();

        try {
            // Some databases may require setting catalog/schema
            connection.setCatalog(database);

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(database, null, "%", new String[]{"TABLE"});

            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tables: " + e.getMessage());
        }

        return tables;
    }
}
