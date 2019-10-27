package de.roamingthings.tracing.novelai.usecases.generate

import de.roamingthings.tracing.novelai.domain.Novel
import de.roamingthings.tracing.novelai.domain.NovelUuid
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.DEFAULT
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.FAILING
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.PARALLEL
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.TEAPOD
import io.opentracing.Tracer
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime.now


@Service
class GenerateNovelService(private val systemClock: Clock,
                           private val tracer: Tracer,
                           private val authorServiceClient: AuthorServiceClient,
                           private val novelLibraryClient: NovelLibraryClient,
                           private val novelTitleService: NovelTitleService) {
    companion object {
        private val log = getLogger(GenerateNovelService::class.java)
    }

    fun generateNovel(method: AuthoringMethod): Novel {
        val novelUuid = NovelUuid()
        log.info("Generating a novel {}", novelUuid)

        val novel = generateNovelInSpanWithIdentifier(novelUuid, method)
        storeNovelInSpan(novel)

        return novel
    }

    private fun storeNovelInSpan(novel: Novel) {
//        val span = tracer.buildSpan("generateNovelContent").start()
//        tracer.activateSpan(span).use {
        tracer.buildSpan("store-novel").startActive(true).use { scope ->
            novelLibraryClient.storeNovel(novel)
        }
    }

    private fun generateNovelInSpanWithIdentifier(novelUuid: NovelUuid, method: AuthoringMethod): Novel {
        tracer.buildSpan("write-novel").startActive(true).use { scope ->
            tracer.activeSpan().setTag("novel_id", novelUuid.toString())

            return generateNovelUsingIdentity(novelUuid, method)
        }
    }

    private fun generateNovelUsingIdentity(novelUuid: NovelUuid, method: AuthoringMethod): Novel {
        val title = retrieveTitle()
        val content = retrieveContentUsingMethod(method)
        return Novel(novelUuid, now(systemClock), title, content)
    }

    private fun retrieveContentUsingMethod(method: AuthoringMethod): String {
        return tracer.buildSpan("write-novel-retrieve-content").startActive(true).use { scope ->
            when (method) {
                DEFAULT -> authorServiceClient.generateNovelContent()
                TEAPOD -> {
                    authorServiceClient.generateNovelContentTeapod()
                    ""
                }
                FAILING -> {
                    authorServiceClient.generateNovelContentFailing()
                    ""
                }
                PARALLEL -> authorServiceClient.generateNovelContentParallel()
            }
        }
    }

    private fun retrieveTitle(): String {
        tracer.buildSpan("write-novel-title").startActive(true).use { scope ->
            return novelTitleService.generateNovelTitle()
        }
    }
}
