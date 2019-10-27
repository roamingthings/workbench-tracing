package de.roamingthings.tracing.author.usecases.generate

import de.roamingthings.tracing.author.test.mockTracingLenientUsingMock
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class FailingGenerateContentServiceTest {
    @Mock
    lateinit var tracer: Tracer;

    @InjectMocks
    lateinit var failingGenerateContentService: FailingGenerateContentService

    @BeforeEach
    fun mockTracing() {
        mockTracingLenientUsingMock(tracer)
    }

    @Test
    fun `should always fail with LazyAuthorDoesNotWantToWorkException`() {
        assertThatThrownBy {
            failingGenerateContentService.generateNovelContent()
        }.isInstanceOf(LazyAuthorDoesNotWantToWorkException::class.java)
    }
}
