package de.roamingthings.tracing.novelai

fun String.trimNonLettersOrDigits(): String = this.trim{ !it.isLetterOrDigit() }
