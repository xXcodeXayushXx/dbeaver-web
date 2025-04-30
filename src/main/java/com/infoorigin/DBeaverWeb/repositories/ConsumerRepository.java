package com.infoorigin.DBeaverWeb.repositories;

import com.infoorigin.DBeaverWeb.entities.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    Optional<Consumer> findByUsername(String username);

    boolean existsByUsername(String username);
}