package de.roamingthings.tracing.novelai.usecases.generate

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.novelai.ports.driven.AuthorServiceClient
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import de.roamingthings.tracing.novelai.ports.driving.NOVEL_AUTHORED
import de.roamingthings.tracing.novelai.ports.driving.NOVEL_TITLE
import de.roamingthings.tracing.novelai.test.mockTracingLenientUsingMock
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.DEFAULT
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.FAILING
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.TEAPOD
import io.opentracing.Tracer
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.web.client.HttpClientErrorException
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
        mockTracingLenientUsingMock(tracer)
    }

    @Test
    fun `should generate novel using SystemClock, NovelTitleService and AuthorServiceClient with default method`() {
        authorServiceReturnsText()
        novelTitleServiceReturnsText()

        val novel = generateNovelService.generateNovel(DEFAULT)

        assertSoftly { softly ->
            softly.assertThat(novel.authored).isEqualTo(NOVEL_AUTHORED)
            softly.assertThat(novel.title).isEqualTo(NOVEL_TITLE)
            softly.assertThat(novel.content).isEqualTo(NOVEL_CONTENT)
        }
        verifyNovelStored()
    }

    @Test
    fun `should throw HttpClientErrorException when using AuthorServiceClient with TEAPOD method`() {
        authorServiceThrowsClientExceptionOnTeapodMethod()
        novelTitleServiceReturnsText()

        Assertions.assertThatThrownBy {
            generateNovelService.generateNovel(TEAPOD)
        }.isInstanceOf(HttpClientErrorException::class.java)
    }

    @Test
    fun `should throw HttpClientErrorException when using AuthorServiceClient with FAILING method`() {
        authorServiceThrowsClientExceptionOnFailingMethod()
        novelTitleServiceReturnsText()

        Assertions.assertThatThrownBy {
            generateNovelService.generateNovel(FAILING)
        }.isInstanceOf(HttpClientErrorException::class.java)
    }

    private fun authorServiceThrowsClientExceptionOnTeapodMethod() {
        doThrow(HttpClientErrorException(I_AM_A_TEAPOT) as Throwable)
                .`when`(authorServiceClient).generateNovelContentTeapod()
    }

    private fun authorServiceThrowsClientExceptionOnFailingMethod() {
        doThrow(HttpClientErrorException(INTERNAL_SERVER_ERROR) as Throwable)
                .`when`(authorServiceClient).generateNovelContentFailing()
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
