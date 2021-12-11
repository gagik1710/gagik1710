package com.demo.notes.domain;

import java.util.Arrays;

public enum Role {
    USER("user"),
    ADMIN("admin");

    public final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role getRole(String name) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(name))
                .findFirst()
                .orElseThrow();
    }
}
