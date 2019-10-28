package de.roamingthings.tracing.novelai.health

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


open class DownstreamHealthIndicator(
        private val restTemplate: RestTemplate,
        private val downstreamServiceName: String,
        private val downStreamUrl: String = "/actuator") : HealthIndicator {

    override fun health(): Health {
        try {
            val response = restTemplate.getForObject("$downStreamUrl/health", JsonNode::class.java)
            if (response != null && response.get("status").asText().equals("UP", ignoreCase = false)) {
                return Health.up().build()
            }
        } catch (ex: Exception) {
            return Health.down(ex).build()
        }

        return Health.down().withDetail("service", downstreamServiceName).build()
    }
}

@Component
class AuthorServiceHealthIndicator(authorServiceRestTemplate: RestTemplate) :
        DownstreamHealthIndicator(authorServiceRestTemplate, "author-service")

@Component
class TextLibraryServiceHealthIndicator(textLibraryServiceRestTemplate: RestTemplate) :
        DownstreamHealthIndicator(textLibraryServiceRestTemplate, "text-library-service")

@Component
class NovelLibraryServiceHealthIndicator(novelLibraryServiceRestTemplate: RestTemplate) :
        DownstreamHealthIndicator(novelLibraryServiceRestTemplate, "novel-library-service")
