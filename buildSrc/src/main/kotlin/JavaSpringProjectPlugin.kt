package de.roamingthings.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

open class JavaSpringProjectPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configureJavaPlugins()
        project.configureSpringBootPlugins()
        project.configureCommonPlugins()
        project.configureJava()
        project.configureJavaDependencies()
        project.configureSpringBootDependencies()
    }
}
