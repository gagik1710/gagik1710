package com.demo.notes.configuration.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.notes.configuration.security.domain.UserDetailsImpl;
import com.demo.notes.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret = "demoappdisqo";

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final var cookie = Arrays.stream(request.getCookies()).filter(it -> it.getName().equals("Authorization"))
                .findFirst()
                .orElseThrow();

        final var token = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(cookie.getValue());

        final var claims = token.getClaims();
        final var id = claims.get("userID").as(Long.class);
        final var email = (String) claims.get("email").as(String.class);
        final var role = Role.getRole(claims.get("role").asString());
        final var userDetails = new UserDetailsImpl(id, email, role);
        final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority(role.value)));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
