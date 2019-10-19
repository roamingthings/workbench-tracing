package de.roamingthings.tracing.novellibrary.ports.driving;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;

class TestDataFactory {
    static StoreNovelRequest aStoreNovelRequest() {
        return new StoreNovelRequest(
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }
}
