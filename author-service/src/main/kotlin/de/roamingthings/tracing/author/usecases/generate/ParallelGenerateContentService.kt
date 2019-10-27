package de.roamingthings.tracing.author.usecases.generate

import de.roamingthings.tracing.author.ports.driven.TextLibraryServiceClient
import io.opentracing.Scope
import io.opentracing.Span
import io.opentracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.stream.Collectors.joining
import java.util.stream.Collectors.toList
import java.util.stream.IntStream.rangeClosed
import kotlin.text.Typography.paragraph

@Service
class ParallelGenerateContentService(private val tracer: Tracer,
                                     private val textLibraryServiceClient: TextLibraryServiceClient,
                                     @Value("\${author.content.num-paragraphs:5}")
                                     private val numParagraphs: Int) {
    companion object {
        private val log = LoggerFactory.getLogger(ParallelGenerateContentService::class.java)
    }

    fun generateNovelContent(): String {
        log.info("Generating a novel in parallel")

        val span = tracer.buildSpan("retrieveParagraphs").start();
        return try {
            tracer.activateSpan(span).use {
                rangeClosed(1, numParagraphs)
                        .parallel()
                        .mapToObj {
                            tracer.scopeManager().activate(span).use {
                                textLibraryServiceClient.retrieveParagraph()?.trim()
                            }
                        }
                        .filter { paragraph -> !paragraph.isNullOrEmpty() }
                        .collect(joining("\n\n"))
            }
        } finally {
            span.finish()
        }
    }
}
