package de.roamingthings.tracing.novellibrary.ports.driven;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.Month.OCTOBER;

class TestDataFactory {
    static UUID NOVEL_UUID = UUID.fromString("400a59e4-d740-4ca9-8d7f-3392451bb99e");
    static LocalDateTime NOVEL_AUTHORED = LocalDateTime.of(2019, OCTOBER, 19, 23, 42);

    @NotNull
    static DbNovel aNovel() {
        return new DbNovel(NOVEL_UUID, now(), NOVEL_AUTHORED, "The Title", "The Content.");
    }
}
