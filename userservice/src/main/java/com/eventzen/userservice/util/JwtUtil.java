package com.eventzen.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "mysecret123mysecret123mysecret123";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(Long userId, String email, String role, String name) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", userId)
                .claim("role", role)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Long getUserId(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public static String getRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public static String getName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    public static String getEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}