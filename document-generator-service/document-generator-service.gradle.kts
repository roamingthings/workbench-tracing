import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    id("com.moowork.node") version "1.3.1"
    id("com.bmuschko.docker-spring-boot-application") version "5.2.0"
}

group = "de.roamingthings.tracing"
version = "0.0.1-SNAPSHOT"

tasks {
    create("clean") {
        dependsOn("npm_run_clean")
        doLast {
            delete(
                    project.buildDir,
                    "node_modules",
                    "dist"
            )
        }
    }
}

tasks["npm_run_build"].dependsOn("npm_i")

tasks.create("dockerBuildImage", DockerBuildImage::class) {
    dependsOn("npm_run_build")
    inputDir.set(file(projectDir))
    tags.add("document-generator-service:latest")
}

tasks.create("build").dependsOn("dockerBuildImage")
