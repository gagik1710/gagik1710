package com.demo.notes.configuration.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.notes.configuration.security.domain.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${demo.app.jwtSecret}")
    private String secret;

    @Value("${demo.app.jwtExpirationMs}")
    private long expirationInMills;

    public String generateToken(final UserDetailsImpl userDetails) {
        final var builder = JWT.create()
                .withClaim("userID", userDetails.getId())
                .withClaim("email", userDetails.getEmail())
                .withClaim("role", userDetails.getRole().value)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationInMills));

        return builder.sign(Algorithm.HMAC256(secret));
    }

}
