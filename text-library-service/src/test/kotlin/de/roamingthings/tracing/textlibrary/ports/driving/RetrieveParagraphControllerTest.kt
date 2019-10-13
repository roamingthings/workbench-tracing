package de.roamingthings.tracing.author.ports.driving

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.textlibrary.ports.driving.PARAGRAPH
import de.roamingthings.tracing.textlibrary.ports.driving.RetrieveParagraphController
import de.roamingthings.tracing.textlibrary.usecases.retrieve.RetrieveParagraphService
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.OK

@ExtendWith(MockitoExtension::class)
class RetrieveParagraphControllerTest {
    @Mock
    lateinit var retrieveParagraphService: RetrieveParagraphService

    @InjectMocks
    lateinit var retrieveParagraphController: RetrieveParagraphController

    @Test
    fun `should return response with paragraph as plain text`() {
        serviceReturnsContent()

        val response = retrieveParagraphController.retrieveRandomParagraph()

        SoftAssertions.assertSoftly {softly ->
            softly.assertThat(response.statusCode).isEqualTo(OK)
            softly.assertThat(response.body).isEqualTo(PARAGRAPH)
        }
    }

    private fun serviceReturnsContent() {
        doReturn(PARAGRAPH)
                .`when`(retrieveParagraphService).retrieveParagraph()
    }
}
