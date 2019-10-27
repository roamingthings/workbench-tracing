package de.roamingthings.tracing.novelai.ports.driving

import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.DEFAULT
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.TEAPOD
import de.roamingthings.tracing.novelai.usecases.generate.GenerateNovelService
import io.opentracing.Tracer
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class GenerateNovelController(
        private val tracer: Tracer,
        private val generateNovelService: GenerateNovelService) {
    companion object {
        private val log = LoggerFactory.getLogger(GenerateNovelController::class.java)
    }

    @PostMapping("/novels")
    fun generateNovel(
            @RequestParam(name = "m", required = false) method: String?
    ): ResponseEntity<Void> {
        val authoringMethod = toAuthoringMethod(method)

        log.info("Generating new novel using method $authoringMethod")
        setAuthoringMethodTag(authoringMethod)

        val novel = generateNovelService.generateNovel(authoringMethod)
        val novelId = novel.uuid

        log.debug("Generated a novel with id $novelId")
        return created(URI("/novels/${novelId}")).build()
    }

    private fun setAuthoringMethodTag(authoringMethod: AuthoringMethod) {
        tracer.activeSpan().setTag("AUTHORING_METHOD", authoringMethod.toString())
    }
}

fun toAuthoringMethod(param: String?): AuthoringMethod =
        when (param?.toLowerCase()) {
            "t" -> TEAPOD
            else -> DEFAULT
        }
