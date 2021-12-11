package com.demo.notes.service;

import com.demo.notes.configuration.security.domain.UserDetailsImpl;
import com.demo.notes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final var user = userRepository.findByEmail(email)
                .orElseThrow(RuntimeException::new);

        return UserDetailsImpl.build(user);
    }
}
