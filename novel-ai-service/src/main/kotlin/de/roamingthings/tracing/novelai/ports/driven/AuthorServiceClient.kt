package de.roamingthings.tracing.novelai.ports.driven

import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthorServiceClient(
        val authorServiceRestTemplate: RestTemplate
) {
    companion object {
        private val log = getLogger(AuthorServiceClient::class.java)
    }

    fun generateNovelContent(): String {
        log.info("Asking author for a new novel content")

        return generateNovelContentUsingUrl("/contents")
    }

    private fun generateNovelContentUsingUrl(serviceUrl: String): String {
        val content = authorServiceRestTemplate.postForObject(serviceUrl, null, String::class.java)
        log.debug("Got novel content {}", content)

        if (content.isNullOrBlank()) {
            throw EmptyContentException()
        }
        return content
    }

    fun generateNovelContentParallel(): String {
        log.info("Asking author for a new novel content in parallel")

        return generateNovelContentUsingUrl("/parallel/contents")
    }

    fun generateNovelContentTeapod() {
        log.info("Asking author for a new novel content using teapod method")

        authorServiceRestTemplate.postForObject("/teapod/contents", null, String::class.java)
    }

    fun generateNovelContentFailing() {
        log.info("Asking author for a new novel content using failing method")

        authorServiceRestTemplate.postForObject("/failing/contents", null, String::class.java)
    }
}
