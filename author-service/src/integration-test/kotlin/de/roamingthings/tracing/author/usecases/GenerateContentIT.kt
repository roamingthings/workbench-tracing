package de.roamingthings.tracing.author.usecases

import de.roamingthings.tracing.testing.mock.TextLibraryServiceMock.Companion.textLibraryServiceMock
import de.roamingthings.tracing.testing.mock.WireMockTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
class GenerateContentIT: WireMockTestBase() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return a novel text`() {
        textLibraryServiceMock.serviceGetsRandomParagraph()

        val performRequest =
                mockMvc.perform(post("/contents")
                        .accept(TEXT_PLAIN))

        performRequest.andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(TEXT_PLAIN))
                .andExpect(content().string("""
                    A random paragraph.

                    A random paragraph.
                    
                    A random paragraph.
                    
                    A random paragraph.
                    
                    A random paragraph.
                """.trimIndent()))
    }

    @Test
    fun `should be a teapod`() {
        val performRequest =
                mockMvc.perform(post("/teapod/contents")
                        .accept(TEXT_PLAIN))

        performRequest.andExpect(status().isIAmATeapot)
    }
}
