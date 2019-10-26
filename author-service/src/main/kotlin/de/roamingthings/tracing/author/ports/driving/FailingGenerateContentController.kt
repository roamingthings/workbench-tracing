package de.roamingthings.tracing.author.ports.driving

import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FailingGenerateContentController {
    @PostMapping("/failing/contents", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun generateContent(): ResponseEntity<String> {
        return status(I_AM_A_TEAPOT).build()
    }
}
