package de.roamingthings.tracing.novellibrary.ports.driving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
class NovelResponse {
    private LocalDateTime authored;

    private String title;

    private String content;
}
