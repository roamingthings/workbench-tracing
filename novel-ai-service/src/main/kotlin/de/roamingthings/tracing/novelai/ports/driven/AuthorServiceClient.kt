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

        return "This is a very short test novel."
    }
}
