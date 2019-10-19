package de.roamingthings.tracing.novelai.ports.driven

import com.fasterxml.jackson.annotation.JsonProperty
import de.roamingthings.tracing.novelai.domain.Novel
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime


@Service
class NovelLibraryClient(
        private val novelLibraryServiceRestTemplate: RestTemplate
) {
    companion object {
        private val log = LoggerFactory.getLogger(NovelLibraryClient::class.java)
    }

    fun storeNovel(novel: Novel) {
        log.info("Storing a novel with uuid {}", novel.uuid)
        val headers = HttpHeaders()
        headers.contentType = APPLICATION_JSON
        val request = HttpEntity(novel.toStoreNovelRequest(), headers)
        novelLibraryServiceRestTemplate.put("/novels/${novel.uuid}", request)
    }
}

fun Novel.toStoreNovelRequest(): StoreNovelRequest {
    return StoreNovelRequest(
            this.uuid.toString(),
            this.authored,
            this.title,
            this.content
    )
}

data class StoreNovelRequest(
        val uuid: String,
        val authored: LocalDateTime,
        val title: String,
        val content: String
)
