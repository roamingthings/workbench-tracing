package de.roamingthings.tracing.novelai.ports.driving

import de.roamingthings.tracing.novelai.domain.NovelUuid
import de.roamingthings.tracing.novelai.usecases.document.GenerateNovelDocumentService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GenerateNovelDocumentController(private val generateNovelDocumentService: GenerateNovelDocumentService) {
    @GetMapping("/novels/{novelUuid}", produces = ["text/asciidoc"])
    fun generateNovelDocument(@PathVariable novelUuid: String): ResponseEntity<String> {
        return ok(generateNovelDocumentService.generateNovelDocument(NovelUuid(novelUuid)))
    }
}
