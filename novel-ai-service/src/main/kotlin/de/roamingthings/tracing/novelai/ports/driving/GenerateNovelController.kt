package de.roamingthings.tracing.novelai.ports.driving

import de.roamingthings.tracing.novelai.usecases.generate.GenerateNovelService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class GenerateNovelController(private val generateNovelService: GenerateNovelService) {
    companion object {
        private val log = LoggerFactory.getLogger(GenerateNovelController::class.java)
    }

    @PostMapping("/novels")
    fun generateNovel(): ResponseEntity<Void> {
        log.info("Generating new novel")

        val novel = generateNovelService.generateNovel()
        val novelId = novel.uuid

        log.debug("Generated a novel with id $novelId")
        return created(URI("/novels/${novelId}")).build()
    }
}
