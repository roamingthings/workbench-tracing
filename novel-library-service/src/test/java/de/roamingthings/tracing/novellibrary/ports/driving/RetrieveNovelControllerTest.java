package de.roamingthings.tracing.novellibrary.ports.driving;

import de.roamingthings.tracing.novellibrary.usecases.retrieve.RetrieveNovelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.*;
import static de.roamingthings.tracing.novellibrary.ports.driving.TestDataFactory.aNovel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class RetrieveNovelControllerTest {
    @Mock
    RetrieveNovelService retrieveNovelService;

    @InjectMocks
    RetrieveNovelController retrieveNovelController;

    @Test
    void should_call_service_to_retrieve_novel() {
        serviceRetrievesNovel();

        ResponseEntity<NovelResponse> response = retrieveNovelController.retrieveNovel(NOVEL_UUID);

        assertThat(response.getBody())
                .isEqualTo(new NovelResponse(NOVEL_AUTHORED, NOVEL_TITLE, NOVEL_CONTENT));
    }

    @Test
    void should_respond_with_no_found_if_service_does_not_find_a_novel() {
        serviceFindsNoNovel();

        ResponseEntity<NovelResponse> response = retrieveNovelController.retrieveNovel(NOVEL_UUID);

        assertThat(response.getStatusCode())
                .isEqualTo(NOT_FOUND);
    }

    private void serviceFindsNoNovel() {
        doReturn(Optional.empty())
                .when(retrieveNovelService).findNovel(NOVEL_UUID);
    }

    private void serviceRetrievesNovel() {
        doReturn(Optional.of(aNovel()))
                .when(retrieveNovelService).findNovel(NOVEL_UUID);
    }
}
