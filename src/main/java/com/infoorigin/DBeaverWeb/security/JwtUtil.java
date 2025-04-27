//package com.infoorigin.DBeaverWeb.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
///**
// * Utility class for handling JWT token operations: generation, validation, and claim extraction.
// */
//@Component
//public class JwtUtil {
//
//    private static final String SECRET = "bTpm8B2l78o2LCcFq3vM7yYxJ5RgNlEjg7Tw5ZNpYVfP4qjMy1UhfypoMxQ7ShTx";
//    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours
//
//    /**
//     * Generates a JWT token for the given username and role.
//     *
//     * @param username The user's username.
//     * @param role     The user's role (e.g., "USER", "ADMIN").
//     * @return The generated JWT token.
//     */
//    public String generateToken(String username, String role) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", role);
//        return createToken(claims, username);
//    }
//
//    /**
//     * Creates a JWT token with the specified claims and subject.
//     *
//     * @param claims  The claims to include in the token.
//     * @param subject The subject (username).
//     * @return The JWT token.
//     */
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    /**
//     * Extracts the username from a JWT token.
//     *
//     * @param token The JWT token.
//     * @return The username.
//     */
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    /**
//     * Extracts the role from a JWT token.
//     *
//     * @param token The JWT token.
//     * @return The role.
//     */
//    public String extractRole(String token) {
//        return extractClaim(token, claims -> claims.get("role", String.class));
//    }
//
//    /**
//     * Extracts a specific claim from a JWT token.
//     *
//     * @param token          The JWT token.
//     * @param claimsResolver The function to extract the claim.
//     * @param <T>           The type of the claim.
//     * @return The extracted claim.
//     */
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    /**
//     * Extracts all claims from a JWT token.
//     *
//     * @param token The JWT token.
//     * @return The claims.
//     */
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    /**
//     * Validates a JWT token against a username.
//     *
//     * @param token    The JWT token.
//     * @param username The username to validate against.
//     * @return True if the token is valid, false otherwise.
//     */
//    public boolean isTokenValid(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//
//    /**
//     * Checks if a JWT token is expired.
//     *
//     * @param token The JWT token.
//     * @return True if expired, false otherwise.
//     */
//    private boolean isTokenExpired(String token) {
//        return extractClaim(token, Claims::getExpiration).before(new Date());
//    }
//}
