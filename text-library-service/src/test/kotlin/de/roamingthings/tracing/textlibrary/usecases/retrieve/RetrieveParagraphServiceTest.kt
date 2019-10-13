package de.roamingthings.tracing.textlibrary.usecases.retrieve

import de.roamingthings.tracing.textlibrary.usecases.generate.PARAGRAPH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class RetrieveParagraphServiceTest {
    @InjectMocks
    lateinit var retrieveParagraphService: RetrieveParagraphService

    @Test
    fun `should retrieve paragraph from repository`() {
        val content = retrieveParagraphService.retrieveParagraph()

        assertThat(content).isEqualTo(PARAGRAPH)
    }
}
