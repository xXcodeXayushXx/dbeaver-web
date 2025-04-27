//
//package com.infoorigin.DBeaverWeb.services;
//
//import com.infoorigin.DBeaverWeb.entities.ConnectionProfile;
//import com.infoorigin.DBeaverWeb.repositories.ConnectionProfileRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ConnectionProfileService {
//
//    private final ConnectionProfileRepository connectionProfileRepository;
//    private final UserService userService;
//    private final DynamicDataSourceService dynamicDataSourceService;
//
//    public ConnectionProfileService(ConnectionProfileRepository connectionProfileRepository,
//                                    UserService userService,
//                                    DynamicDataSourceService dynamicDataSourceService) {
//        this.connectionProfileRepository = connectionProfileRepository;
//        this.userService = userService;
//        this.dynamicDataSourceService = dynamicDataSourceService;
//    }
//
//    /**
//     * Saves a new connection profile for the authenticated user.
//     *
//     * @param profile The connection profile to save.
//     * @return The saved connection profile.
//     * @throws IllegalArgumentException if validation fails.
//     */
//    public ConnectionProfile saveConnectionProfile(ConnectionProfile profile) {
//        validateProfile(profile);
//        Long userId = userService.getCurrentUserId();
//        profile.setUserId(userId);
//        return connectionProfileRepository.save(profile);
//    }
//
//    /**
//     * Retrieves all connection profiles for the authenticated user.
//     *
//     * @return A list of connection profiles.
//     */
//    public List<ConnectionProfile> getConnectionProfiles() {
//        Long userId = userService.getCurrentUserId();
//        return connectionProfileRepository.findByUserId(userId);
//    }
//
//    /**
//     * Retrieves a connection profile by ID, ensuring it belongs to the authenticated user.
//     *
//     * @param id The profile ID.
//     * @return The connection profile.
//     * @throws IllegalArgumentException if the profile is not found or unauthorized.
//     */
//    public ConnectionProfile getConnectionProfileById(Long id) {
//        Long userId = userService.getCurrentUserId();
//        ConnectionProfile profile = connectionProfileRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + id));
//        if (!profile.getUserId().equals(userId)) {
//            throw new IllegalArgumentException("Unauthorized access to profile: " + id);
//        }
//        return profile;
//    }
//
//    /**
//     * Deletes a connection profile by ID, ensuring it belongs to the authenticated user.
//     *
//     * @param id The profile ID.
//     * @throws IllegalArgumentException if the profile is not found or unauthorized.
//     */
//    public void deleteConnectionProfile(Long id) {
//        Long userId = userService.getCurrentUserId();
//        ConnectionProfile profile = connectionProfileRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Profile not found: " + id));
//        if (!profile.getUserId().equals(userId)) {
//            throw new IllegalArgumentException("Unauthorized access to profile: " + id);
//        }
//        connectionProfileRepository.deleteById(id);
//    }
//
//    /**
//     * Creates a database connection using a saved profile.
//     *
//     * @param profileId The ID of the connection profile.
//     * @return The connection ID.
//     * @throws IllegalArgumentException if the profile is not found or unauthorized.
//     */
//    public String connectUsingProfile(Long profileId) {
//        ConnectionProfile profile = getConnectionProfileById(profileId);
//        return dynamicDataSourceService.createDataSource(
//                profile.getHost(),
//                profile.getPort(),
//                profile.getDatabase(),
//                profile.getUsername(),
//                profile.getPassword(),
//                profile.getDriverClassName()
//        );
//    }
//
//    private void validateProfile(ConnectionProfile profile) {
//        if (profile.getName() == null || profile.getName().isEmpty()) {
//            throw new IllegalArgumentException("Profile name is required");
//        }
//        if (profile.getHost() == null || profile.getHost().isEmpty()) {
//            throw new IllegalArgumentException("Host is required");
//        }
//        if (profile.getPort() == null || profile.getPort().isEmpty()) {
//            throw new IllegalArgumentException("Port is required");
//        }
//        if (profile.getDatabase() == null || profile.getDatabase().isEmpty()) {
//            throw new IllegalArgumentException("Database name is required");
//        }
//        if (profile.getUsername() == null || profile.getUsername().isEmpty()) {
//            throw new IllegalArgumentException("Username is required");
//        }
//        if (profile.getDriverClassName() == null || profile.getDriverClassName().isEmpty()) {
//            throw new IllegalArgumentException("Driver class name is required");
//        }
//    }
//}