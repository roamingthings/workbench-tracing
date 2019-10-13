package de.roamingthings.tracing.novelai.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate



@Configuration
class ServiceClientConfiguration {
    @Value("\${novelai.author-service.base-uri")
    lateinit var authorServiceBaseUri: String

    @Bean
    fun authorServiceRestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(authorServiceBaseUri).build()
    }
}
