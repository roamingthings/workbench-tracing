package de.roamingthings.tracing.textlibrary.ports.driven

interface TextRepository {
    fun loadRandomText(): String
}
