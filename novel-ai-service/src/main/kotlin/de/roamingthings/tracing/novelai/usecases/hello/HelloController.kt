package de.roamingthings.tracing.novelai.usecases.hello

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    companion object {
        private val log = LoggerFactory.getLogger(HelloController::class.java)
    }

    @GetMapping("/hello", produces = [TEXT_PLAIN_VALUE])
    fun sayHello(): ResponseEntity<String> {
        log.info("About to say hello")
        return ok("Hello!")
    }
}
