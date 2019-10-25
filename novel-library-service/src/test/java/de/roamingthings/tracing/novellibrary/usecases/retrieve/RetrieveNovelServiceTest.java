package de.roamingthings.tracing.novellibrary.usecases.retrieve;

import de.roamingthings.tracing.novellibrary.domain.Novel;
import de.roamingthings.tracing.novellibrary.ports.driven.NovelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static de.roamingthings.tracing.novellibrary.CommonTestDataFactory.NOVEL_UUID;
import static de.roamingthings.tracing.novellibrary.usecases.retrieve.TestDataFactory.aDbNovel;
import static de.roamingthings.tracing.novellibrary.usecases.retrieve.TestDataFactory.aNovel;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RetrieveNovelServiceTest {
    @Mock
    NovelRepository novelRepository;

    @InjectMocks
    RetrieveNovelService retrieveNovelService;

    @Test
    void should_retrieve_novel_from_repository() {
        repositoryFindsNovel();

        var novel = retrieveNovelService.findNovel(NOVEL_UUID);

        assertSoftly(softly -> {
            softly.assertThat(novel.isPresent()).isTrue();
            softly.assertThat(novel.get()).isEqualTo(aNovel());
        });
    }

    private void repositoryFindsNovel() {
        doReturn(Optional.of(aDbNovel()))
                .when(novelRepository).findById(NOVEL_UUID);
    }
}
