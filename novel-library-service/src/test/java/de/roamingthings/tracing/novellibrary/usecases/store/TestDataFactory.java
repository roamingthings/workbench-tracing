package de.roamingthings.tracing.novellibrary.usecases.store;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;

class TestDataFactory {

    static StoreNovelCommand aStoreNovelCommand() {
        return new StoreNovelCommand(
                NOVEL_UUID,
                NOVEL_AUTHORED,
                NOVEL_TITLE,
                NOVEL_CONTENT
        );
    }
}
