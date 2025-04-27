//package com.infoorigin.DBeaverWeb.entities;
//
//import jakarta.annotation.Generated;
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.relational.core.mapping.Table;
//
//import java.time.LocalDateTime;
//
//@Data
//@Table("query_history")
//public class QueryHistory {
//    @Id
//    private Long id;
//    private Long userId; // Foreign key to User
//    private Long connectionProfileId; // Foreign key to ConnectionProfile (optional)
//    private String query; // SQL query text
//    private LocalDateTime executedAt; // Timestamp of execution
//    private boolean successful; // Whether the query succeeded
//    private String errorMessage; // Error message if the query failed
//}
