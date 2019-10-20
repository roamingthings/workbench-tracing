package de.roamingthings.tracing.novelai.ports.driven

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TextLibraryServiceClient(
        private val textLibraryServiceRestTemplate: RestTemplate
) {
    companion object {
        private val log = LoggerFactory.getLogger(TextLibraryServiceClient::class.java)
    }

    fun retrieveParagraph(): String? {
        log.info("Getting a random paragraph")

        val content = textLibraryServiceRestTemplate.getForObject("/paragraphs/random", String::class.java)
        log.debug("Got paragraph: \"{}\"", content)

        return content
    }
}
