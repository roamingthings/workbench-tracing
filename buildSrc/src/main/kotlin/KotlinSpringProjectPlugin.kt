package de.roamingthings.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

open class KotlinSpringProjectPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configureKotlinPlugins()
        project.configureSpringBootPlugins()
        project.configureCommonPlugins()
        project.configureJava()
        project.configureKotlin()
        project.configureKotlinDependencies()
        project.configureSpringBootDependencies()
    }
}
