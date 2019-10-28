package de.roamingthings.tracing.author.usecases.generate

import de.roamingthings.tracing.author.ports.driven.TextLibraryServiceClient
import io.opentracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GenerateContentService(private val tracer: Tracer,
                             private val textLibraryServiceClient: TextLibraryServiceClient,
                             @Value("\${author.content.num-paragraphs:5}")
                             private val numParagraphsDefault: Int) {
    companion object {
        private val log = LoggerFactory.getLogger(GenerateContentService::class.java)
    }

    fun generateNovelContent(numParagraphs: Int?): String {
        log.info("Generating a novel")

        val paragraphs = mutableListOf<String>()

        val numParagraphsToFetch = numParagraphs ?: numParagraphsDefault

        val span = tracer.buildSpan("retrieveParagraphs").start();
        try {
            tracer.activateSpan(span).use {
                for (index in 1..numParagraphsToFetch) {
                    textLibraryServiceClient.retrieveParagraph()?.let {
                        paragraphs.add(it.trim())
                    }
                }
            }
            return paragraphs.joinToString("\n\n")
        } finally {
            span.finish()
        }
    }
}
