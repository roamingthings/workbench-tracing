package de.roamingthings.tracing.author.ports.driving

import de.roamingthings.tracing.author.usecases.generate.ParallelGenerateContentService
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ParallelGenerateContentController(private val parallelGenerateContentService: ParallelGenerateContentService) {
    @PostMapping("/parallel/contents", produces = [TEXT_PLAIN_VALUE])
    fun generateContent(
            @RequestParam(name = "p", required = false) numParagraphs: Int?
    ): ResponseEntity<String> {
        val content = parallelGenerateContentService.generateNovelContent(numParagraphs)
        return ok(content)
    }
}
