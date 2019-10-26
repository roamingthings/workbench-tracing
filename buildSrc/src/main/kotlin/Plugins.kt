package de.roamingthings.gradle

import org.gradle.api.Project

internal fun Project.configureKotlinPlugins() {
    plugins.apply("org.jetbrains.kotlin.jvm")
    plugins.apply("org.jetbrains.kotlin.plugin.spring")
}

internal fun Project.configureJavaPlugins() {
    plugins.apply("org.gradle.java")
}

internal fun Project.configureSpringBootPlugins() {
    plugins.apply("org.springframework.boot")
    plugins.apply("io.spring.dependency-management")
    plugins.apply("org.gradle.maven-publish")
}

internal fun Project.configureCommonPlugins() {
    plugins.apply("org.gradle.maven-publish")
}
