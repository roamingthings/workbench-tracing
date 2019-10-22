package de.roamingthings.tracing.novelai.usecases

import de.roamingthings.tracing.testing.mock.AuthorServiceMock.Companion.authorServiceMock
import de.roamingthings.tracing.testing.mock.DocumentGeneratorServiceMock.Companion.documentGeneratorServiceMock
import de.roamingthings.tracing.testing.mock.NovelLibraryServiceMock.Companion.novelLibraryServiceMock
import de.roamingthings.tracing.testing.mock.TextLibraryServiceMock.Companion.textLibraryServiceMock
import de.roamingthings.tracing.testing.mock.WireMockTestBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
class GenerateNovelIT: WireMockTestBase() {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @Disabled
    fun `should generate novel`() {
        authorServiceMock.serviceGeneratesContent()
        novelLibraryServiceMock.serviceStoresNovel()
        novelLibraryServiceMock.serviceRetrievesNovel()
        textLibraryServiceMock.retrievesRandomParagraph()
        documentGeneratorServiceMock.serviceGeneratesDocument()

        val performCreateNovel = mockMvc.perform(post("/novels"))

        val location = performCreateNovel
                .andExpect(status().isCreated)
                .andReturn()
                .response.getHeader("Location")

        assertThat(location).isNotNull()

        val performCreateDocument = mockMvc.perform(get(location!!))

        val document = performCreateDocument
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith("text/asciidoc"))
                .andReturn()
                .response.contentAsString

        assertThat(document).isEqualTo("""
                = The Novel Title
        
                The novel paragraph.
        
                Another Paragraph.
            """.trimIndent()
        )

    }
}
