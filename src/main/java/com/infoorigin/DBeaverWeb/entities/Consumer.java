package com.infoorigin.DBeaverWeb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

// consumers Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consumers")

public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "consumer", cascade = CascadeType.ALL)
    private List<ConnectionProfile> connectionProfiles;

}
