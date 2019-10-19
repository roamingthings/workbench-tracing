package de.roamingthings.tracing.novellibrary.ports.driving;

import de.roamingthings.tracing.novellibrary.usecases.store.StoreNovelCommand;
import de.roamingthings.tracing.novellibrary.usecases.store.StoreNovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class StoreNovelController {
    private final StoreNovelService storeNovelService;

    @PutMapping(value = "/novels/{novelUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> storeNovel(
            @PathVariable UUID novelUuid,
            @Valid @RequestBody StoreNovelRequest createRequest
    ) {
        storeNovelService.storeNovel(toStoreCommand(novelUuid, createRequest));
        return ok().build();
    }

    private StoreNovelCommand toStoreCommand(UUID novelUuid, StoreNovelRequest request) {
        return new StoreNovelCommand(
                novelUuid,
                request.getAuthored(),
                request.getTitle(),
                request.getContent()
        );
    }
}
