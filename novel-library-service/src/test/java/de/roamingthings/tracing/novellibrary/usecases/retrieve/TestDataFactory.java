package de.roamingthings.tracing.novellibrary.usecases.retrieve;

import de.roamingthings.tracing.novellibrary.domain.Novel;
import de.roamingthings.tracing.novellibrary.ports.driven.DbNovel;

import java.time.LocalDateTime;
import java.time.Month;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.NOVEL_UUID;

class TestDataFactory {

    static final LocalDateTime NOVEL_CREATED = LocalDateTime.of(2019, Month.OCTOBER, 25, 23, 42);
    static final LocalDateTime NOVEL_AUTHORED = LocalDateTime.of(2019, Month.OCTOBER, 25, 23, 43);
    static final String NOVEL_TITLE = "A Title";
    static final String NOVEL_CONTENT = "The Novel content.\n\nIn a nutshell.";

    static Novel aNovel() {
        return new Novel(
                NOVEL_UUID,
                NOVEL_CREATED,
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }

    static DbNovel aDbNovel() {
        return new DbNovel(
                NOVEL_UUID,
                NOVEL_CREATED,
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }
}
