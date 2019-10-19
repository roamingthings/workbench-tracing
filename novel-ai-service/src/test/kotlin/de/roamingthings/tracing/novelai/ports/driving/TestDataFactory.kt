package de.roamingthings.tracing.novelai.ports.driving

import de.roamingthings.tracing.novelai.domain.Novel
import de.roamingthings.tracing.novelai.domain.NovelUuid
import java.time.LocalDateTime
import java.time.LocalDateTime.of
import java.time.Month.OCTOBER

val NOVEL_UUID = NovelUuid("ae357ee4-f64d-4995-aba1-474abc81ffe9")
val NOVEL_AUTHORED: LocalDateTime = of(2019, OCTOBER, 19, 23, 42)
const val NOVEL_TITLE = "A Test Novel"
const val NOVEL_CONTENT = "This is a very short test novel."

fun aNovel() = Novel(NOVEL_UUID, NOVEL_AUTHORED, NOVEL_TITLE, NOVEL_CONTENT)
