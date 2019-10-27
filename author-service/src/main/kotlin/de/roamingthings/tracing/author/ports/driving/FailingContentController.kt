package de.roamingthings.tracing.author.ports.driving

import de.roamingthings.tracing.author.usecases.generate.FailingGenerateContentService
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FailingContentController(private val failingGenerateContentService: FailingGenerateContentService) {
    @PostMapping("/failing/contents", produces = [TEXT_PLAIN_VALUE])
    fun generateContent() {
        failingGenerateContentService.generateNovelContent()
    }
}
