package com.demo.notes.configuration.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginPayload {
    private String email;
    private String password;
}
