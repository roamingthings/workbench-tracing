package de.roamingthings.tracing.novellibrary.usecases.store;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StoreNovelCommand {
    private UUID uuid;
    private LocalDateTime authored;
    private String title;
    private String content;
}
