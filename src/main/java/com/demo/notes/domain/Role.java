package com.demo.notes.domain;

public enum Role {
    USER("user"),
    ADMIN("admin");

    public final String value;

    Role(String value) {
        this.value = value;
    }
}
