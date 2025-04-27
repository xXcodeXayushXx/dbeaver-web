//package com.infoorigin.DBeaverWeb.controllers;
//
//
//import com.infoorigin.DBeaverWeb.entities.User;
//import com.infoorigin.DBeaverWeb.security.JwtUtil;
//import com.infoorigin.DBeaverWeb.services.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final UserService userService;
//
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userService = userService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
//            );
//            String role = authentication.getAuthorities().iterator().next().getAuthority();
//            String jwt = jwtUtil.generateToken(loginRequest.username(), role);
//            return ResponseEntity.ok(new LoginResponse(jwt, loginRequest.username(), role));
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
//        }
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
//        try {
//            User user = new User();
//            user.setUsername(registerRequest.username());
//            user.setPassword(registerRequest.password());
//            user.setRole(registerRequest.role() != null ? registerRequest.role() : "USER");
//            User savedUser = userService.createUser(user);
//            return ResponseEntity.ok(Map.of("id", savedUser.getId(), "status", "registered"));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(Map.of("error", "Registration failed: " + e.getMessage()));
//        }
//    }
//}
//
//record LoginRequest(String username, String password) {}
//record LoginResponse(String token, String username, String role) {}
//record RegisterRequest(String username, String password, String role) {}
