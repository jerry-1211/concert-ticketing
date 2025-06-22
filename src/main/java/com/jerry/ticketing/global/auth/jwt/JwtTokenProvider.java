package com.jerry.ticketing.global.auth.jwt;

import com.jerry.ticketing.global.auth.config.JwtConfig;
import com.jerry.ticketing.global.auth.oauth.CustomOauth2User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    public Key getSigningKey() {
        byte[] bytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(bytes);
    }


    public String generateToken(CustomOauth2User customOauth2User) {
        Date expiryDate = new Date(System.currentTimeMillis() + jwtConfig.getExpiration());

        return Jwts.builder()
                .setSubject(customOauth2User.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
