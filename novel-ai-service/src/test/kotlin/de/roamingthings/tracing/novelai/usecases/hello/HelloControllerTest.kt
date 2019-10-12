package de.roamingthings.tracing.novelai.usecases.hello

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.OK

@ExtendWith(MockitoExtension::class)
class HelloControllerTest {
    @Test
    fun `should say hello`() {
        val controller = HelloController()

        val response = controller.sayHello()

        assertThat(response.statusCode).isEqualTo(OK)
        assertThat(response.body).isEqualTo("Hello!")
    }
}
