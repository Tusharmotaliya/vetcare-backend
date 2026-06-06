package com.vetcare.vetcare_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

// @Component means Spring manages this class like @Service
// We can @Autowired it wherever we need JWT operations
@Component
public class JwtUtil {

    // Secret key used to sign and verify tokens
    // In production this should be in application.properties, not hardcoded
    // Must be at least 32 characters for HS256 algorithm
    private static final String SECRET = "vetcare-super-secret-key-2024-india-saas";

    // Token valid for 24 hours (in milliseconds)
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate a JWT token for a clinic after successful login
    // subject = clinic's email — this is embedded in the token
    public String generateToken(String email, Long clinicId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("clinicId", clinicId)   // embed clinicId in token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey())
                .compact();
    }

    // Extract the email from a token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract clinicId from a token
    public Long extractClinicId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("clinicId", Long.class);
    }

    // Check if token is valid and not expired
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}