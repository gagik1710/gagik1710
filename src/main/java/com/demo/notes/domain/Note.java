package com.demo.notes.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Note {
    private Long id;
    private String title;
    private String note;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastUpdateDate;
}
