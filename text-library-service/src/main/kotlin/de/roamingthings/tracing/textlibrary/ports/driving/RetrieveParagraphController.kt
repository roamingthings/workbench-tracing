package de.roamingthings.tracing.textlibrary.ports.driving

import de.roamingthings.tracing.textlibrary.usecases.retrieve.RetrieveParagraphService
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RetrieveParagraphController(private val retrieveParagraphService: RetrieveParagraphService) {
    @GetMapping("/paragraphs/random", produces = [TEXT_PLAIN_VALUE])
    fun retrieveRandomParagraph(): ResponseEntity<String> {
        val paragraph = retrieveParagraphService.retrieveParagraph()
        return ok(paragraph)
    }
}
