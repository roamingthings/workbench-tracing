package de.roamingthings.tracing.novellibrary.usecases.store;

import de.roamingthings.tracing.novellibrary.CommonTestDataFactory;
import de.roamingthings.tracing.novellibrary.ports.driven.DbNovel;
import de.roamingthings.tracing.novellibrary.ports.driven.NovelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;
import static de.roamingthings.tracing.novellibrary.usecases.store.TestDataFactory.*;
import static java.time.Clock.fixed;
import static java.time.ZoneId.systemDefault;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoreNovelServiceTest {

    Clock systemClock = fixed(NOVEL_CREATED.atZone(systemDefault()).toInstant(), systemDefault());

    @Mock
    NovelRepository novelRepository;

    @InjectMocks
    StoreNovelService storeNovelService;

    @BeforeEach
    void setup() {
        storeNovelService = new StoreNovelService(novelRepository, systemClock);
    }

    @Test
    void should_save_novel_to_repository() {
        StoreNovelCommand command = aStoreNovelCommand();

        storeNovelService.storeNovel(command);

        verify(novelRepository)
                .save(new DbNovel(
                        NOVEL_UUID,
                        NOVEL_CREATED,
                        NOVEL_AUTHORED,
                        NOVEL_TITLE,
                        NOVEL_CONTENT
                ));
    }
}
