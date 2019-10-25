package de.roamingthings.tracing.novellibrary.usecases.retrieve;

import de.roamingthings.tracing.novellibrary.domain.Novel;
import de.roamingthings.tracing.novellibrary.ports.driven.DbNovel;
import de.roamingthings.tracing.novellibrary.ports.driven.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveNovelService {
    private final NovelRepository novelRepository;

    public Optional<Novel> findNovel(UUID novelUuid) {
        return novelRepository.findById(novelUuid)
                .map(this::toNovel);
    }

    @NotNull
    private Novel toNovel(DbNovel dbNovel) {
        return new Novel(
                dbNovel.getUuid(),
                dbNovel.getCreated(),
                dbNovel.getAuthored(),
                dbNovel.getTitle(),
                dbNovel.getContent()
        );
    }
}
