package de.roamingthings.tracing.novelai.usecases.generate

import de.roamingthings.tracing.novelai.domain.Novel
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import io.opentracing.Tracer
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service


@Service
class GenerateNovelService(private val tracer: Tracer,
                           private val authorServiceClient: AuthorServiceClient) {
    companion object {
        private val log = getLogger(GenerateNovelService::class.java)
    }

    fun generateNovel(): Novel {
        log.info("Generating a novel")

//        val span = tracer.buildSpan("generateNovelContent").start()
//        tracer.activateSpan(span).use {
        tracer.buildSpan("generateNovelContent").startActive(true).use { scope ->
            val content = authorServiceClient.generateNovelContent()

            return Novel(title = "A static novel", content = content)
        }
    }
}
