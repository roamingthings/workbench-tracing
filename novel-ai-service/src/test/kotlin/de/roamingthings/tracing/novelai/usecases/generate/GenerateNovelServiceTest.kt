package de.roamingthings.tracing.novelai.usecases.generate

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import de.roamingthings.tracing.novelai.ports.driving.NOVEL_AUTHORED
import de.roamingthings.tracing.novelai.ports.driving.NOVEL_TITLE
import io.opentracing.Scope
import io.opentracing.Span
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Clock.fixed
import java.time.ZoneId.systemDefault

@ExtendWith(MockitoExtension::class)
class GenerateNovelServiceTest {
    @Mock
    lateinit var tracer: Tracer;

    @Mock
    lateinit var novelTitleService: NovelTitleService

    @Mock
    lateinit var authorServiceClient: AuthorServiceClient

    @Mock
    lateinit var novelLibraryClient: NovelLibraryClient

    val systemClock = fixed(NOVEL_AUTHORED.atZone(systemDefault()).toInstant(), systemDefault())

    lateinit var generateNovelService: GenerateNovelService

    @BeforeEach
    fun setup() {
        generateNovelService = GenerateNovelService(
                systemClock,
                tracer,
                authorServiceClient,
                novelLibraryClient,
                novelTitleService)
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
        doReturn(spanMock)
                .`when`(tracer).activeSpan()
//        doReturn(scopeMock)
//                .`when`(tracer).activateSpan(any())
    }

    @Test
    fun `should generate novel using SystemClock, NovelTitleService and AuthorServiceClient`() {
        authorServiceReturnsText()
        novelTitleServiceReturnsText()

        val novel = generateNovelService.generateNovel()

        assertSoftly { softly ->
            softly.assertThat(novel.authored).isEqualTo(NOVEL_AUTHORED)
            softly.assertThat(novel.title).isEqualTo(NOVEL_TITLE)
            softly.assertThat(novel.content).isEqualTo(NOVEL_CONTENT)
        }
        verifyNovelStored()
    }

    private fun verifyNovelStored() {
        verify(novelLibraryClient)
                .storeNovel(any())
    }

    private fun novelTitleServiceReturnsText() {
        doReturn(NOVEL_TITLE)
                .`when`(novelTitleService).generateNovelTitle()
    }

    private fun authorServiceReturnsText() {
        doReturn(NOVEL_CONTENT)
                .`when`(authorServiceClient).generateNovelContent()
    }
}
