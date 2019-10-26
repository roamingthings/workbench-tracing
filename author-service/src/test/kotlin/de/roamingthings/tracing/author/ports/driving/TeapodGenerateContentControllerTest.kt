package de.roamingthings.tracing.author.ports.driving

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus


class TeapodGenerateContentControllerTest {
    @Test
    fun `should respond with 418`() {
        val controller = TeapodGenerateContentController()

        val response = controller.generateContent()

        assertThat(response.statusCode).isEqualTo(HttpStatus.I_AM_A_TEAPOT)
    }

}
