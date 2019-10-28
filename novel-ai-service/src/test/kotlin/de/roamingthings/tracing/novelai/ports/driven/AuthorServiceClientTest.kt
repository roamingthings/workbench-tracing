package de.roamingthings.tracing.novelai.ports.driven

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.novelai.NOVEL_CONTENT
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
internal class AuthorServiceClientTest {
    @Mock
    lateinit var authorServiceRestTemplate: RestTemplate

    @InjectMocks
    lateinit var authorServiceClient: AuthorServiceClient

    @Test
    fun `should return body of response for default service`() {
        serviceReturnsContentBody(NOVEL_CONTENT)

        val content = authorServiceClient.generateNovelContent(null)

        assertThat(content)
                .isEqualTo(NOVEL_CONTENT)
    }

    @Test
    fun `should return body of response for default service with num paragraphs`() {
        serviceReturnsContentBodyWithOptions(NOVEL_CONTENT)

        val content = authorServiceClient.generateNovelContent(42)

        assertThat(content)
                .isEqualTo(NOVEL_CONTENT)
    }

    @Test
    fun `should return body of response for parallel service`() {
        parallelServiceReturnsContentBody(NOVEL_CONTENT)

        val content = authorServiceClient.generateNovelContentParallel(null)

        assertThat(content)
                .isEqualTo(NOVEL_CONTENT)
    }

    @Test
    fun `should return body of response for parallel service with num paragraphs`() {
        parallelServiceReturnsContentBodyWithOptions(NOVEL_CONTENT)

        val content = authorServiceClient.generateNovelContentParallel(42)

        assertThat(content)
                .isEqualTo(NOVEL_CONTENT)
    }

    @Test
    fun `should throw EmptyContentException if response for default service responds with empty body`() {
        serviceReturnsContentBody("")

        assertThatThrownBy {
            authorServiceClient.generateNovelContent(null)
        }.isInstanceOf(EmptyContentException::class.java)
    }

    @Test
    fun `should throw EmptyContentException if response for default service responds without body`() {
        serviceReturnsContentBody(null)

        assertThatThrownBy {
            authorServiceClient.generateNovelContent(null)
        }.isInstanceOf(EmptyContentException::class.java)
    }

    private fun serviceReturnsContentBody(body: String?) {
        doReturn(body)
                .`when`(authorServiceRestTemplate).postForObject("/contents", null, String::class.java)
    }

    private fun parallelServiceReturnsContentBody(body: String?) {
        doReturn(body)
                .`when`(authorServiceRestTemplate).postForObject("/parallel/contents", null, String::class.java)
    }

    private fun serviceReturnsContentBodyWithOptions(body: String?) {
        doReturn(body)
                .`when`(authorServiceRestTemplate).postForObject("/contents?p={numParagraphs}", null, String::class.java, 42)
    }

    private fun parallelServiceReturnsContentBodyWithOptions(body: String?) {
        doReturn(body)
                .`when`(authorServiceRestTemplate).postForObject("/parallel/contents?p={numParagraphs}", null, String::class.java, 42)
    }
}
