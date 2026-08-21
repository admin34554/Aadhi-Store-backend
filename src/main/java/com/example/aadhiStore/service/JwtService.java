package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.UserMaster;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserMaster user) {

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("companyId", user.getCompanyMaster().getId())
                .claim("companyName", user.getCompanyMaster().getName())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractCompanyId(String token) {
        return extractAllClaims(token).get("companyId", Long.class);
    }

    public String extractCompanyName(String token) {
        return extractAllClaims(token).get("companyName", String.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserMaster user) {

        String username = extractUsername(token);

        return username.equals(user.getUsername())
                && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);

    }

    public boolean validateToken(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;

        }

    }
}