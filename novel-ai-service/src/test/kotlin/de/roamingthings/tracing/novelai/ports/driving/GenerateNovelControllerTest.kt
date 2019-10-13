package de.roamingthings.tracing.novelai.ports.driving

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.novelai.usecases.generate.GenerateNovelService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.CREATED

@ExtendWith(MockitoExtension::class)
class GenerateNovelControllerTest {
    @Mock
    lateinit var generateNovelService: GenerateNovelService

    @InjectMocks
    lateinit var generateNovelController: GenerateNovelController

    @Test
    fun `should return location with generated novel`() {
        serviceReturnsNovel()

        val response = generateNovelController.generateNovel()

        assertThat(response.statusCode).isEqualTo(CREATED)
        assertThat(response.headers["Location"]).containsExactly("/novels/${NOVEL_UUID}")
    }

    private fun serviceReturnsNovel() {
        doReturn(aNovel())
                .`when`(generateNovelService).generateNovel()
    }
}
