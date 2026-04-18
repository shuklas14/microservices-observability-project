package com.authentication.auth_service.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey";

    public String generateToken(String username) {

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }
}