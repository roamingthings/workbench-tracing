package de.roamingthings.tracing.novellibrary.domain;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class Novel {
    private UUID uuid;
    private LocalDateTime created;
    private LocalDateTime authored;
    private String title;
    private String content;
}
