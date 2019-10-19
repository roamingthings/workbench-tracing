package de.roamingthings.tracing.textlibrary.usecases.retrieve

import de.roamingthings.tracing.textlibrary.ports.driven.TextRepository
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

@Service
class RetrieveParagraphService(private val textRepository: TextRepository) {
    companion object {
        private val log = getLogger(RetrieveParagraphService::class.java)
    }

    fun retrieveParagraph(): String {
        log.info("Looking for some paragraph")

        return textRepository.loadRandomText()
    }
}
