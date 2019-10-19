package de.roamingthings.tracing.novellibrary.ports.driving;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class StoreNovelRequest {
    @NotNull
    private LocalDateTime authored;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
