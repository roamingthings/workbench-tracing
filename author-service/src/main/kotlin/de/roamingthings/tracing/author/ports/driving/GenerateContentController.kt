package de.roamingthings.tracing.author.ports.driving

import de.roamingthings.tracing.author.usecases.generate.GenerateContentService
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GenerateContentController(private val generateContentService: GenerateContentService) {
    @PostMapping("/contents", produces = [TEXT_PLAIN_VALUE])
    fun generateContent(
            @RequestParam(name = "p", required = false) numParagraphs: Int?
    ): ResponseEntity<String> {
        val content = generateContentService.generateNovelContent(numParagraphs)
        return ok(content)
    }
}
