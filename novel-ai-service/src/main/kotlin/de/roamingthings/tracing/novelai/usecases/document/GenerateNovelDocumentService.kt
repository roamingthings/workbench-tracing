package de.roamingthings.tracing.novelai.usecases.document

import de.roamingthings.tracing.novelai.domain.NovelUuid
import de.roamingthings.tracing.novelai.ports.driven.DocumentGeneratorClient
import de.roamingthings.tracing.novelai.ports.driven.GenerateDocumentSpecification
import de.roamingthings.tracing.novelai.ports.driven.NovelLibraryClient
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

@Service
class GenerateNovelDocumentService(
        val novelLibraryClient: NovelLibraryClient,
        val documentGeneratorClient: DocumentGeneratorClient
) {
    companion object {
        private val log = getLogger(GenerateNovelDocumentService::class.java)
    }

    fun generateNovelDocument(novelUuid: NovelUuid): String? {
        val novel = novelLibraryClient.retrieveNovel(novelUuid)
        return if (novel != null) {
            documentGeneratorClient.generateDocument(GenerateDocumentSpecification(
                    novel.authored, novel.title, novel.content
            ))
        } else {
            null
        }
    }
}
