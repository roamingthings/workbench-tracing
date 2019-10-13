package de.roamingthings.tracing.author.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@Configuration
class ServiceClientConfiguration(
    @Value("\${author.text-library-service.base-uri}")
    private val textLibraryServiceBaseUri: String
) {
    @Bean
    fun textLibraryServiceRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(textLibraryServiceBaseUri).build()
    }
}
