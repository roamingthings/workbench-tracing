package de.roamingthings.tracing.novelai.usecases

import de.roamingthings.tracing.testing.mock.AuthorServiceMock
import de.roamingthings.tracing.testing.mock.AuthorServiceMock.Companion.authorServiceMock
import de.roamingthings.tracing.testing.mock.WireMockTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class GenerateNovelIT: WireMockTestBase() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should generate document`() {
        authorServiceMock.serviceGeneratesContent()

        val performRequest = mockMvc.perform(post("/novels"))

        performRequest.andExpect(status().isCreated)
                .andExpect(header().exists("Location"))
    }
}
