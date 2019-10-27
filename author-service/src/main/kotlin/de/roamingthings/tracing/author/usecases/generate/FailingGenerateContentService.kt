package de.roamingthings.tracing.author.usecases.generate

import io.opentracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FailingGenerateContentService(private val tracer: Tracer) {
    companion object {
        private val log = LoggerFactory.getLogger(FailingGenerateContentService::class.java)
    }

    fun generateNovelContent(): String {
        log.info("I don't want to work today")
        tracer.buildSpan("retrieveParagraph").startActive(true).use { scope ->
            throw LazyAuthorDoesNotWantToWorkException()
        }
    }
}

class LazyAuthorDoesNotWantToWorkException : RuntimeException()
