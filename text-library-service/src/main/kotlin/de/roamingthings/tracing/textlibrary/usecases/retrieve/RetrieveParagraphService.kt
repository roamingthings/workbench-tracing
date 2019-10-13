package de.roamingthings.tracing.textlibrary.usecases.retrieve

import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

@Service
class RetrieveParagraphService {
    companion object {
        private val log = getLogger(RetrieveParagraphService::class.java)
    }

    fun retrieveParagraph(): String {
        log.info("Looking for some paragraph")

        return "A random paragraph."
    }
}
