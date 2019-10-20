package de.roamingthings.tracing.textlibrary.usecases.retrieve

import com.nhaarman.mockitokotlin2.doReturn
import de.roamingthings.tracing.textlibrary.ports.driven.TextRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class RetrieveParagraphServiceTest {
    @Mock
    lateinit var textRepository: TextRepository

    @InjectMocks
    lateinit var retrieveParagraphService: RetrieveParagraphService

    @Test
    fun `should retrieve paragraph from repository`() {
        repositoryFindsText()

        val content = retrieveParagraphService.retrieveParagraph()

        assertThat(content).isEqualTo(PARAGRAPH)
    }

    @Test
    fun `should throw exception if null is retrieved`() {
        repositoryDoesntFindText()

        assertThatThrownBy {
            retrieveParagraphService.retrieveParagraph()
        }.isInstanceOf(NoTextFoundException::class.java)
    }

    @Test
    fun `should throw exception if blank paragraph is retrieved`() {
        repositoryFindsBlankParagraph()

        assertThatThrownBy {
            retrieveParagraphService.retrieveParagraph()
        }.isInstanceOf(NoTextFoundException::class.java)
    }

    private fun repositoryFindsBlankParagraph() {
        doReturn("")
                .`when`(textRepository).loadRandomText()
    }

    private fun repositoryFindsText() {
        doReturn(PARAGRAPH)
                .`when`(textRepository).loadRandomText()
    }

    private fun repositoryDoesntFindText() {
        doReturn(null)
                .`when`(textRepository).loadRandomText()
    }
}
