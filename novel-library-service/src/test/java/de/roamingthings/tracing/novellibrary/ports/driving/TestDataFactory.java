package de.roamingthings.tracing.novellibrary.ports.driving;

import de.roamingthings.tracing.novellibrary.domain.Novel;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;

class TestDataFactory {
    static StoreNovelRequest aStoreNovelRequest() {
        return new StoreNovelRequest(
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }

    static Novel aNovel() {
        return new Novel(
                NOVEL_UUID,
                NOVEL_CREATED,
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }
}
