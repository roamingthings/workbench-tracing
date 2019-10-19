package de.roamingthings.tracing.novellibrary;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class CommonTestDataFactory {
    public static final UUID NOVEL_UUID = UUID.fromString("fcea267a-31df-472a-ae0e-8afda51d648d");
    public static final LocalDateTime NOVEL_CREATED = LocalDateTime.of(2019, Month.OCTOBER, 19, 23, 42);
    public static final LocalDateTime NOVEL_AUTHORED = LocalDateTime.of(2019, Month.OCTOBER, 17, 23, 42);
    public static final String NOVEL_TITLE = "A Novel Title";
    public static final String NOVEL_CONTENT = "A Novel Title";
}
