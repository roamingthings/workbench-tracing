package de.roamingthings.tracing.author.ports.driving

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.author.usecases.generate.GenerateContentService
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
    lateinit var generateContentService: GenerateContentService

    @InjectMocks
    lateinit var generateContentController: GenerateContentController

    @Test
    fun `should return response with content as plain text`() {
        serviceReturnsContent()

        val response = generateContentController.generateContent()

        SoftAssertions.assertSoftly {softly ->
            softly.assertThat(response.statusCode).isEqualTo(OK)
            softly.assertThat(response.body).isEqualTo(NOVEL_CONTENT)
        }
    }

    private fun serviceReturnsContent() {
        doReturn(NOVEL_CONTENT)
                .`when`(generateContentService).generateNovelContent()
    }
}
