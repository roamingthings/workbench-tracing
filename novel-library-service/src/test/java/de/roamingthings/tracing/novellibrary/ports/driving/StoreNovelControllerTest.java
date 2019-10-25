package de.roamingthings.tracing.novellibrary.ports.driving;

import de.roamingthings.tracing.novellibrary.usecases.store.StoreNovelCommand;
import de.roamingthings.tracing.novellibrary.usecases.store.StoreNovelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;
import static de.roamingthings.tracing.novellibrary.ports.driving.TestDataFactory.aStoreNovelRequest;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoreNovelControllerTest {
    @Mock
    StoreNovelService storeNovelService;

    @InjectMocks
    StoreNovelController storeNovelController;

    @Test
    void should_call_service_to_store_novel() {
        StoreNovelRequest request = aStoreNovelRequest();

        storeNovelController.storeNovel(NOVEL_UUID, request);

        verifyServiceCalledWithCommand();
    }

    private void verifyServiceCalledWithCommand() {
        verify(storeNovelService)
                .storeNovel(new StoreNovelCommand(
                        NOVEL_UUID,
                        NOVEL_AUTHORED,
                        NOVEL_TITLE,
                        NOVEL_CONTENT
                ));
    }
}
