package de.roamingthings.tracing.novelai.usecases.document

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import de.roamingthings.tracing.novelai.ports.driven.DocumentGeneratorClient
import de.roamingthings.tracing.novelai.ports.driven.GenerateDocumentSpecification
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import de.roamingthings.tracing.novelai.ports.driving.NOVEL_UUID
import de.roamingthings.tracing.novelai.ports.driving.aNovel
import io.opentracing.Scope
import io.opentracing.Span
import io.opentracing.Tracer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class GenerateNovelDocumentServiceTest {
    @Mock
    lateinit var novelLibraryClient: NovelLibraryClient

    @Mock
    lateinit var documentGeneratorClient: DocumentGeneratorClient

    @InjectMocks
    lateinit var generateNovelDocumentService: GenerateNovelDocumentService

    @Test
    fun `should generate document with novel retrieved from novel library`() {
        novelLibraryClientRetrievesNovel()
        documentGeneratorClientGeneratesDocument()

        val generatedDocument = generateNovelDocumentService.generateNovelDocument(NOVEL_UUID)

        assertThat(generatedDocument).isEqualTo(aNovelDocument())
    }

    private fun novelLibraryClientRetrievesNovel() {
        doReturn(aNovel())
                .`when`(novelLibraryClient).retrieveNovel(NOVEL_UUID)
    }

    private fun documentGeneratorClientGeneratesDocument() {
        doReturn(aNovelDocument())
                .`when`(documentGeneratorClient).generateDocument(
                        GenerateDocumentSpecification(
                                NOVEL_AUTHORED,
                                NOVEL_TITLE,
                                NOVEL_CONTENT
                        ))
    }
}
