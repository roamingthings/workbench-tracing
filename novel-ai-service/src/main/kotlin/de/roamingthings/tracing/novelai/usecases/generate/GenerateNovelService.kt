package de.roamingthings.tracing.novelai.usecases.generate

import de.roamingthings.tracing.novelai.domain.Novel
import de.roamingthings.tracing.novelai.domain.NovelUuid
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import io.opentracing.Tracer
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import java.time.LocalDateTime.now


@Service
class GenerateNovelService(private val tracer: Tracer,
                           private val authorServiceClient: AuthorServiceClient,
                           private val novelLibraryClient: NovelLibraryClient) {
    companion object {
        private val log = getLogger(GenerateNovelService::class.java)
    }

    fun generateNovel(): Novel {
        val novelUuid = NovelUuid()
        log.info("Generating a novel {}", novelUuid)

//        val span = tracer.buildSpan("generateNovelContent").start()
//        tracer.activateSpan(span).use {
        tracer.buildSpan("generateNovelContent").startActive(true).use { scope ->
            tracer.activeSpan().setTag("NOVEL_ID", novelUuid.toString())

            val content = authorServiceClient.generateNovelContent()

            val novel = Novel(novelUuid, now(), "A static novel", content)

            novelLibraryClient.storeNovel(novel)

            return novel
        }
    }
}
