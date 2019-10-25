package de.roamingthings.tracing.novellibrary.ports.driving;

import de.roamingthings.tracing.novellibrary.domain.Novel;
import de.roamingthings.tracing.novellibrary.usecases.retrieve.RetrieveNovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class RetrieveNovelController {
    private final RetrieveNovelService retrieveNovelService;

    @GetMapping(value = "/novels/{novelUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NovelResponse> retrieveNovel(
            @PathVariable UUID novelUuid
    ) {

        var novel = retrieveNovelService.findNovel(novelUuid);

        if (novel.isPresent()) {
            return ok(toNovelResponse(novel.get()));
        } else {
            return notFound().build();
        }
    }

    private NovelResponse toNovelResponse(Novel novel) {
        return new NovelResponse(
                novel.getAuthored(),
                novel.getTitle(),
                novel.getContent()
        );
    }
}
