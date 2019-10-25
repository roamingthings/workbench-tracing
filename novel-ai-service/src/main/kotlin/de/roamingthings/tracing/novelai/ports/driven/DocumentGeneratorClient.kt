package de.roamingthings.tracing.novelai.ports.driven

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException
import java.time.LocalDateTime

@Service
class DocumentGeneratorClient(val documentGeneratorServiceRestTemplate: RestTemplate) {
    fun generateDocument(generateDocumentSpecification: GenerateDocumentSpecification): String {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType("text", "asciidoc"))
        val request = HttpEntity(generateDocumentSpecification, headers)

        return documentGeneratorServiceRestTemplate.postForObject("/documents", request, String::class.java)
                ?: throw DocumentGeneratorServiceException()
    }
}

data class GenerateDocumentSpecification(
        val authored: LocalDateTime,
        val title: String,
        val content: String
)

class DocumentGeneratorServiceException:RuntimeException()
