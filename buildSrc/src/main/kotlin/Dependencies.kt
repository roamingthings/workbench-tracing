package de.roamingthings.gradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.springframework.boot.gradle.plugin.SpringBootPlugin

const val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
const val jetbrainsAnnotations = "org.jetbrains:annotations:16.0.2"
const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
const val lombok = "org.projectlombok:lombok"
const val springBootAnnotationProcessor = "org.springframework.boot:spring-boot-configuration-processor"
const val springBootStarterActuator = "org.springframework.boot:spring-boot-starter-actuator"
const val springBootStarterTest = "org.springframework.boot:spring-boot-starter-test"
const val springBootStarterWeb = "org.springframework.boot:spring-boot-starter-web"

internal fun Project.configureKotlinDependencies() {
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${extra["mockitoKotlinVersion"]}"

    dependencies {
        add("implementation", jacksonModuleKotlin)

        add("implementation", kotlinReflect)
        add("implementation", kotlinStdlib)

        add("testImplementation", mockitoKotlin)
    }
}

internal fun Project.configureSpringBootDependencies() = dependencies {
    add("implementation", springBootStarterActuator)
    add("implementation", springBootStarterWeb)
    add("implementation", jetbrainsAnnotations)

    add("testImplementation", springBootStarterTest)

    add("annotationProcessor", springBootAnnotationProcessor)
}

internal fun Project.configureJavaDependencies() = dependencies {
    add("implementation", lombok)
    add("annotationProcessor", lombok)
}

internal fun Project.containsSpringBootPlugin(): Boolean {
    return project.plugins.toList().any { plugin -> plugin is SpringBootPlugin }
}
