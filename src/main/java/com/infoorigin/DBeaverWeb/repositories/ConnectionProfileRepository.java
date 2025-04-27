package com.infoorigin.DBeaverWeb.repositories;

import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing ConnectionProfile entities.
 */
public interface ConnectionProfileRepository extends CrudRepository<ConnectionProfile, Long> {
}