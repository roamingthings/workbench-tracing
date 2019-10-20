package de.roamingthings.tracing.novelai.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@Configuration
class ServiceClientConfiguration(
    @Value("\${novelai.author-service.base-uri}")
    private val authorServiceBaseUri: String,
    @Value("\${novelai.novel-library-service.base-uri}")
    private val novelLibraryServiceBaseUri: String,
    @Value("\${novelai.text-library-service.base-uri}")
    private val textLibraryServiceBaseUri: String
) {
    @Bean
    fun authorServiceRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(authorServiceBaseUri).build()
    }

    @Bean
    fun novelLibraryServiceRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(novelLibraryServiceBaseUri).build()
    }

    @Bean
    fun textLibraryServiceRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(textLibraryServiceBaseUri).build()
    }
}
