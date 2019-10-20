package de.roamingthings.tracing.novelai.usecases.generate

import de.roamingthings.tracing.novelai.ports.driven.TextLibraryServiceClient
import de.roamingthings.tracing.novelai.trimNonLettersOrDigits
import org.springframework.stereotype.Service
import java.util.Random

@Service
class NovelTitleService(
        private val randomNumberGenerator: Random,
        private val textLibraryServiceClient: TextLibraryServiceClient
) {

    fun generateNovelTitle(): String {
        val word = wordFrom(textLibraryServiceClient.retrieveParagraph())
        return if (word.isNullOrBlank()) throw NoContentException() else word
    }

    private fun wordFrom(paragraph: String?): String? {
        if (paragraph.isNullOrBlank()) return null

        val words =
                paragraph.trimNonLettersOrDigits()
                        .split("[\\W]+".toRegex())
        return words[randomNumberGenerator.nextInt(words.size)]
    }
}

class NoContentException : RuntimeException()
