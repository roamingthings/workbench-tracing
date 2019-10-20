package de.roamingthings.tracing.novelai.usecases.generate

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.novelai.ports.driven.TextLibraryServiceClient
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Random

@ExtendWith(MockitoExtension::class)
class NovelTitleServiceTest {
    @Mock
    lateinit var textLibraryServiceClient: TextLibraryServiceClient

    @Mock
    lateinit var randomNumberGenerator: Random

    @InjectMocks
    lateinit var novelTitleService: NovelTitleService

    @Test
    fun `should get title using paragraph from text library`() {
        textLibraryClientRetrievesParagraph()
        randomNumberIsGenerated()

        val title = novelTitleService.generateNovelTitle()

        assertSoftly { softly ->
            softly.assertThat(title).isEqualTo("dolor")
            softly.assertThat(title).isNotBlank
            softly.assertThat(title).doesNotContainAnyWhitespaces()
        }
    }

    @Test
    fun `should throw exception if retrieved paragraph is null`() {
        textLibraryClientRetrievesNull()

        assertThatThrownBy {
            novelTitleService.generateNovelTitle()
        }.isInstanceOf(NoContentException::class.java)
    }

    @Test
    fun `should throw exception if retrieved paragraph is empty`() {
        textLibraryClientRetrievesEmptyString()

        assertThatThrownBy {
            novelTitleService.generateNovelTitle()
        }.isInstanceOf(NoContentException::class.java)
    }

    private fun randomNumberIsGenerated() {
        doReturn(2)
                .`when`(randomNumberGenerator).nextInt(5)
    }

    private fun textLibraryClientRetrievesNull() {
        doReturn(null)
                .`when`(textLibraryServiceClient).retrieveParagraph()
    }

    private fun textLibraryClientRetrievesEmptyString() {
        doReturn("")
                .`when`(textLibraryServiceClient).retrieveParagraph()
    }

    private fun textLibraryClientRetrievesParagraph() {
        doReturn(TEXT_LIBRARY_PARAGRAPH)
                .`when`(textLibraryServiceClient).retrieveParagraph()
    }
}
