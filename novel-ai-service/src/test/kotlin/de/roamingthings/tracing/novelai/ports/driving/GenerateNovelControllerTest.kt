package de.roamingthings.tracing.novelai.ports.driving

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import de.roamingthings.tracing.novelai.test.mockTracingLenientUsingMock
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.DEFAULT
import de.roamingthings.tracing.novelai.usecases.generate.AuthoringMethod.TEAPOD
import de.roamingthings.tracing.novelai.usecases.generate.GenerateNovelService
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.CREATED

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

        val response = generateNovelController.generateNovel(null)

        assertThat(response.statusCode).isEqualTo(CREATED)
        assertThat(response.headers["Location"]).containsExactly("/novels/${NOVEL_UUID}")
        verify(generateNovelService)
                .generateNovel(eq(DEFAULT))
    }

    @Test
    fun `should pass method DEFAULT to service if no request parameter given`() {
        serviceReturnsNovel()

        generateNovelController.generateNovel(null)

        verify(generateNovelService)
                .generateNovel(eq(DEFAULT))
    }

    @Test
    fun `should pass method TEAPOD to service if method parameter 't' given`() {
        serviceReturnsNovel()

        generateNovelController.generateNovel("t")

        verify(generateNovelService)
                .generateNovel(eq(TEAPOD))
    }

    private fun serviceReturnsNovel() {
        doReturn(aNovel())
                .`when`(generateNovelService).generateNovel(any())
    }
}
