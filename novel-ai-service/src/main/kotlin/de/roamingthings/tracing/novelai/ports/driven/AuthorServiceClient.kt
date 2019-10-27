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

        val content = authorServiceRestTemplate.postForObject("/contents", null, String::class.java)
        log.debug("Got novel content {}", content)

        if (content == null) {
            throw EmptyContentException()
        }

        return content
    }

    fun generateNovelContentTeapod() {
        log.info("Asking author for a new novel content using teapod method")

        authorServiceRestTemplate.postForObject("/teapod/contents", null, String::class.java)
    }
}
