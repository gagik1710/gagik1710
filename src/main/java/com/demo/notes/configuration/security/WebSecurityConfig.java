package com.demo.notes.configuration.security;

import com.demo.notes.configuration.security.filter.JwtAuthenticationFilter;
import com.demo.notes.configuration.security.filter.JwtAuthorizationFilter;
import com.demo.notes.configuration.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfig())
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").hasAuthority("admin")
                .antMatchers("/note/**").hasAnyAuthority("user", "admin")
                .antMatchers("/job/**").hasAnyAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManagerInit() throws Exception {
        return authenticationManager();
    }

    public CorsConfigurationSource corsConfig() {
        final var source = new UrlBasedCorsConfigurationSource();
        final var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of(CorsConfiguration.ALL));
        corsConfig.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(List.of("Authorization", "Access-Control-Allow-Origin", "Content-Type", "Cache-Control", "X-Requested-With"));
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
