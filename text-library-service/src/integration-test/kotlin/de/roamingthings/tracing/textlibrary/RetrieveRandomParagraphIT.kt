package de.roamingthings.tracing.textlibrary

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.textlibrary.ports.driven.DbText
import de.roamingthings.tracing.textlibrary.ports.driven.MongoTextRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Random

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
class RetrieveRandomParagraphIT {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @MockBean
    lateinit var randomNumberGenerator: Random

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should get a random paragraph`() {
        randomNumberGeneratorGeneratesRandomNumbers()
        somePersistedText()

        val performRequest = mockMvc.perform(get("/paragraphs/random"))

        performRequest.andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(TEXT_PLAIN))
                .andExpect(content().string("A random paragraph."))
    }

    private fun randomNumberGeneratorGeneratesRandomNumbers() {
        doReturn(1)
                .`when`(randomNumberGenerator).nextInt(any())
    }

    private fun somePersistedText() {
        mongoTemplate.dropCollection("texts")
        mongoTemplate.insert(DbText(1, "A random paragraph."), "texts")
    }
}
