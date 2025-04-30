package com.infoorigin.DBeaverWeb.repositories;

import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface ConnectionProfileRepository extends JpaRepository<ConnectionProfile, Long> {

    List<ConnectionProfile> findByConsumerId(Long consumerId);
}