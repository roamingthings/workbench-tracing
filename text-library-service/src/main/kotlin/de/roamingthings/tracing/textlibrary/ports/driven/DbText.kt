package de.roamingthings.tracing.textlibrary.ports.driven

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "texts")
data class DbText(
        val ordinal: Int,
        val text: String
)
