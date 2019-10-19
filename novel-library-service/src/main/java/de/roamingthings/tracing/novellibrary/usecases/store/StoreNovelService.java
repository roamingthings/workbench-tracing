package de.roamingthings.tracing.novellibrary.usecases.store;

import de.roamingthings.tracing.novellibrary.ports.driven.DbNovel;
import de.roamingthings.tracing.novellibrary.ports.driven.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class StoreNovelService {
    private final NovelRepository novelRepository;
    private final Clock systemClock;

    public void storeNovel(StoreNovelCommand storeNovelCommand) {
        novelRepository.save(
                new DbNovel(
                        storeNovelCommand.getUuid(),
                        now(systemClock),
                        storeNovelCommand.getAuthored(),
                        storeNovelCommand.getTitle(),
                        storeNovelCommand.getContent()
                )
        );
    }
}
