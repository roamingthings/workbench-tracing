package de.roamingthings.tracing.novelai.ports.driving

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.novelai.test.mockTracingLenientUsingMock
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.DEFAULT
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.FAILING
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.PARALLEL
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.TEAPOD
import de.roamingthings.tracing.novelai.usecases.generate.GenerateNovelService
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.I_AM_A_TEAPOT
import org.springframework.web.client.HttpClientErrorException

@ExtendWith(MockitoExtension::class)
class GenerateNovelControllerTest {
    @Mock
    lateinit var tracer: Tracer

    @Mock
    lateinit var generateNovelService: GenerateNovelService

    @InjectMocks
    lateinit var generateNovelController: GenerateNovelController

    @BeforeEach
    fun mockTracing() {
        mockTracingLenientUsingMock(tracer)
    }

    @Test
    fun `should return location with generated novel`() {
        serviceReturnsNovel()

        val response = generateNovelController.generateNovel(null, null)

        assertThat(response.statusCode).isEqualTo(CREATED)
        assertThat(response.headers["Location"]).containsExactly("/novels/${NOVEL_UUID}")
        verify(generateNovelService)
                .generateNovel(DEFAULT, null)
    }

    @Test
    fun `should pass method DEFAULT to service if no request parameter given`() {
        serviceReturnsNovel()

        generateNovelController.generateNovel(null, null)

        verify(generateNovelService)
                .generateNovel(DEFAULT, null)
    }

    @Test
    fun `should pass method TEAPOD to service if method parameter 't' given`() {
        serviceThrowsClientException(I_AM_A_TEAPOT)

        assertThatThrownBy {
            generateNovelController.generateNovel("t", null)
        }.isInstanceOf(HttpClientErrorException::class.java)

        verify(generateNovelService)
                .generateNovel(TEAPOD, null)
    }

    @Test
    fun `should pass method FAILING to service if method parameter 'f' given`() {
        serviceThrowsClientException(INTERNAL_SERVER_ERROR)

        assertThatThrownBy {
            generateNovelController.generateNovel("f", null)
        }.isInstanceOf(HttpClientErrorException::class.java)

        verify(generateNovelService)
                .generateNovel(FAILING, null)
    }

    @Test
    fun `should pass method PARALLEL to service if method parameter 'p' given`() {
        serviceReturnsNovel()

        generateNovelController.generateNovel("p", null)

        verify(generateNovelService)
                .generateNovel(PARALLEL, null)
    }

    @Test
    fun `should pass number of paragraphs from parameter 'p'`() {
        serviceReturnsNovel()
        val numParagraphs = 42

        generateNovelController.generateNovel(null, numParagraphs)

        verify(generateNovelService)
                .generateNovel(any(), eq(42))
    }

    private fun serviceThrowsClientException(httpStatus: HttpStatus) {
        doThrow(HttpClientErrorException(httpStatus) as Throwable)
                .`when`(generateNovelService).generateNovel(any(), anyOrNull())
    }

    private fun serviceReturnsNovel() {
        doReturn(aNovel())
                .`when`(generateNovelService).generateNovel(any(), anyOrNull())
    }
}
