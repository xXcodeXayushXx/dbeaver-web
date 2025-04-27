//package com.infoorigin.DBeaverWeb.security;
//
//
//import com.infoorigin.DBeaverWeb.entities.User;
//import com.infoorigin.DBeaverWeb.repositories.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
///**
// * Service to load user details from the UserRepository for Spring Security authentication.
// */
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    /**
//     * Loads user details by username.
//     *
//     * @param username The username to look up.
//     * @return UserDetails for the user.
//     * @throws UsernameNotFoundException if the user is not found.
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                Collections.singletonList(() -> user.getRole())
//        );
//    }
//}
