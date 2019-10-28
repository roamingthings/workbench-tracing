package de.roamingthings.tracing.author.ports.driving

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.author.usecases.generate.ParallelGenerateContentService
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.OK

@ExtendWith(MockitoExtension::class)
class ParallelGenerateContentControllerTest {
    @Mock
    lateinit var parallelGenerateContentService: ParallelGenerateContentService

    @InjectMocks
    lateinit var parallelGenerateContentController: ParallelGenerateContentController

    @Test
    fun `should return response with content as plain text`() {
        serviceReturnsContent()

        val response = parallelGenerateContentController.generateContent(null)

        SoftAssertions.assertSoftly {softly ->
            softly.assertThat(response.statusCode).isEqualTo(OK)
            softly.assertThat(response.body).isEqualTo(NOVEL_CONTENT)
        }
    }

    @Test
    fun `should pass number of paragraphs from parameter 'p'`() {
        serviceReturnsContent()
        val numParagraphs = 42

        parallelGenerateContentController.generateContent(numParagraphs)

        verify(parallelGenerateContentService)
                .generateNovelContent(42)
    }

    private fun serviceReturnsContent() {
        doReturn(NOVEL_CONTENT)
                .`when`(parallelGenerateContentService).generateNovelContent(anyOrNull())
    }
}
