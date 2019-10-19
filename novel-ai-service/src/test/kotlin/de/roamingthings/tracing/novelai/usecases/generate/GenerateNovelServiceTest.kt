package de.roamingthings.tracing.novelai.usecases.generate

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import io.opentracing.Scope
import io.opentracing.Span
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GenerateNovelServiceTest {
    @Mock
    lateinit var tracer: Tracer;

    @Mock
    lateinit var authorServiceClient: AuthorServiceClient

    @Mock
    lateinit var novelLibraryClient: NovelLibraryClient

    @InjectMocks
    lateinit var generateNovelService: GenerateNovelService

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
        doReturn(spanMock)
                .`when`(tracer).activeSpan()
//        doReturn(scopeMock)
//                .`when`(tracer).activateSpan(any())
    }

    @Test
    fun `should retrieve novel text via AuthorServiceClient`() {
        authorServiceReturnsText()

        val novel = generateNovelService.generateNovel()

        assertThat(novel.content).isEqualTo(NOVEL_CONTENT)
        verifyNovelStored()
    }

    private fun verifyNovelStored() {
        verify(novelLibraryClient)
                .storeNovel(any())
    }

    private fun authorServiceReturnsText() {
        doReturn(NOVEL_CONTENT)
                .`when`(authorServiceClient).generateNovelContent()
    }
}
