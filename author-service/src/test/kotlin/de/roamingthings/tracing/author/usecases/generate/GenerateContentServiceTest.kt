package de.roamingthings.tracing.author.usecases.generate

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.author.ports.driven.TextLibraryServiceClient
import de.roamingthings.tracing.author.test.mockTracingLenientUsingMock
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GenerateContentServiceTest {
    @Mock
    lateinit var tracer: Tracer;

    @Mock
    lateinit var textLibraryServiceClient: TextLibraryServiceClient

    lateinit var generateContentService: GenerateContentService

    @BeforeEach
    fun initService() {
        generateContentService = GenerateContentService(tracer, textLibraryServiceClient, 5)
    }

    @BeforeEach
    fun mockTracing() {
        mockTracingLenientUsingMock(tracer)
    }

    @Test
    fun `should retrieve paragraph from TextLibraryClient with default number of paragraphs`() {
        textLibraryServiceReturnsText()

        val content = generateContentService.generateNovelContent(null)

        assertThat(content).isEqualTo("""
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
        """.trimIndent())
    }

    @Test
    fun `should retrieve paragraph from TextLibraryClient with provided number of paragraphs`() {
        textLibraryServiceReturnsText()
        val numParagraphs = 2

        val content = generateContentService.generateNovelContent(numParagraphs)

        assertThat(content).isEqualTo("""
            A test paragraph.
            
            A test paragraph.
        """.trimIndent())
    }

    private fun textLibraryServiceReturnsText() {
        doReturn(TEXT_LIBRARY_CONTENT)
                .`when`(textLibraryServiceClient).retrieveParagraph()
    }
}
