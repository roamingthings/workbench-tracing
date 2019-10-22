package de.roamingthings.tracing.novelai.ports.driving

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.novelai.usecases.document.GenerateNovelDocumentService
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.OK

@ExtendWith(MockitoExtension::class)
class GenerateNovelDocumentControllerTest {

    @Mock
    lateinit var generateNovelDocumentService: GenerateNovelDocumentService

    @InjectMocks
    lateinit var generateNovelDocumentController: GenerateNovelDocumentController

    @Test
    fun `should return an AsciiDoc document containing the novel`() {
        serviceReturnsGeneratedDocument()

        val response = generateNovelDocumentController.generateNovelDocument(NOVEL_UUID.toString())

        assertSoftly { softly ->
            softly.assertThat(response.statusCode).isEqualTo(OK)
            softly.assertThat(response.body).isEqualTo("""
                = The Novel Title
        
                The novel paragraph.
        
                Another Paragraph.
            """.trimIndent())
        }
    }

    private fun serviceReturnsGeneratedDocument() {
        doReturn(aNovelDocument())
                .`when`(generateNovelDocumentService).generateNovelDocument(NOVEL_UUID)
    }
}
