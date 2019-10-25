package de.roamingthings.tracing.novelai.ports.driven

import de.roamingthings.tracing.novelai.domain.Novel
import de.roamingthings.tracing.novelai.domain.NovelUuid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
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
        novelLibraryServiceRestTemplate.put("/novels/{uuid}", request, novel.uuid.toString())
    }

    fun retrieveNovel(uuid: NovelUuid): Novel? {
        log.info("Retrieve a novel with uuid {}", uuid.toString())
        try {
            return novelLibraryServiceRestTemplate.getForObject("/novels/{uuid}", Novel::class.java, uuid.toString())
        } catch (exception: HttpClientErrorException) {
            if (exception.statusCode == HttpStatus.NOT_FOUND) {
                return null
            }
            throw exception
        }
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
