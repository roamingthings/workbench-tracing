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
        val span = tracer.buildSpan("store-novel").start()
        tracer.activateSpan(span).use {
            try {
                novelLibraryClient.storeNovel(novel)
            } finally {
                span.finish()
            }
        }
    }

    private fun generateNovelInSpanWithIdentifier(novelUuid: NovelUuid, method: AuthoringMethod): Novel {
        val span = tracer.buildSpan("write-novel").start()
        tracer.activateSpan(span).use {
            try {
                tracer.activeSpan().setTag("novel_id", novelUuid.toString())
                tracer.activeSpan().setBaggageItem("novel_id", novelUuid.toString())

                return generateNovelUsingIdentity(novelUuid, method)
            } finally {
                span.finish()
            }
        }
    }

    private fun generateNovelUsingIdentity(novelUuid: NovelUuid, method: AuthoringMethod): Novel {
        val title = retrieveTitle()
        val content = retrieveContentUsingMethod(method)
        return Novel(novelUuid, now(systemClock), title, content)
    }

    private fun retrieveContentUsingMethod(method: AuthoringMethod): String {
        val span = tracer.buildSpan("write-novel-retrieve-content").start()
        tracer.activateSpan(span).use {
            try {
                return when (method) {
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
            } finally {
                span.finish()
            }
        }
    }

    private fun retrieveTitle(): String {
        val span = tracer.buildSpan("write-novel-title").start()
        tracer.activateSpan(span).use {
            try {
                return novelTitleService.generateNovelTitle()
            } finally {
                span.finish()
            }
        }
    }
}
