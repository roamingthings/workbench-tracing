package de.roamingthings.tracing.novellibrary.ports.driven;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static de.roamingthings.tracing.novellibrary.ports.driven.TestDataFactory.NOVEL_UUID;
import static de.roamingthings.tracing.novellibrary.ports.driven.TestDataFactory.aNovel;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NovelRepositoryIT {
    @Autowired
    NovelRepository novelRepository;

    @Test
    void shouldStoreNovel() {
        DbNovel novel = aNovel();

        novelRepository.save(novel);

        Optional<DbNovel> actualNovel = novelRepository.findById(NOVEL_UUID);
        assertThat(actualNovel.isPresent()).isTrue();
        assertThat(actualNovel.get()).isEqualTo(novel);
    }
}
