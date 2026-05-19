package rado.alberto.org.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import rado.alberto.org.config.JwtProperties;
import rado.alberto.org.variables.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtProperties.secret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(
            Long userId,
            String email,
            Role role
    ) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role.name())
                .issuedAt(Date.from(now))
                .expiration(
                        Date.from(
                                now.plusMillis(
                                        jwtProperties.accessTokenExpirationMs()
                                )
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims validateToken(String token) {
        return extractClaims(token);
    }

    public Long extractId(String token) {
        return extractClaims(token)
                .get("userId", Long.class);
    }

    public String extractUsername(String token) {
        return extractClaims(token)
                .getSubject();
    }

    public Role extractRole(String token) {
        String role = extractClaims(token)
                .get("role", String.class);

        return Role.valueOf(role);
    }

    public boolean isTokenValid(String token) {

        try {
            extractClaims(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
