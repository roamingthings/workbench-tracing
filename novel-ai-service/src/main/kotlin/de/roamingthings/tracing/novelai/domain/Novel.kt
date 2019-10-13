package de.roamingthings.tracing.novelai.domain

import java.time.LocalDate
import java.time.LocalDate.now
import java.util.UUID
import java.util.UUID.fromString
import java.util.UUID.randomUUID

data class Novel(
        val uuid: NovelUuid = NovelUuid(),
        val created: LocalDate = now(),
        val title: String,
        val content: String)

data class NovelUuid(val uuid: UUID = randomUUID()) {

    constructor(uuid: String) : this(fromString(uuid))

    override fun toString(): String = uuid.toString()
}
