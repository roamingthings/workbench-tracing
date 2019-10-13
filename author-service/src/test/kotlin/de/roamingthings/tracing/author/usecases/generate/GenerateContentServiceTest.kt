package de.roamingthings.tracing.author.usecases.generate

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import de.roamingthings.tracing.author.ports.driven.TextLibraryServiceClient
import io.opentracing.Scope
import io.opentracing.Span
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
        val spanMock = mock<Span>()
        val scopeMock = mock<Scope>()
        val builderMock = mock<Tracer.SpanBuilder> {
//            on { start() } doReturn spanMock
            on { startActive(any()) } doReturn scopeMock
        }
        doReturn(builderMock)
                .`when`(tracer).buildSpan(any())
//        doReturn(scopeMock)
//                .`when`(tracer).activateSpan(any())
    }

    @Test
    fun `should retrieve paragraph from TextLibraryClient`() {
        textLibraryServiceReturnsText()

        val content = generateContentService.generateNovelContent()

        assertThat(content).isEqualTo("""
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
            
            A test paragraph.
        """.trimIndent())
    }

    private fun textLibraryServiceReturnsText() {
        doReturn(TEXT_LIBRARY_CONTENT)
                .`when`(textLibraryServiceClient).retrieveParagraph()
    }
}
