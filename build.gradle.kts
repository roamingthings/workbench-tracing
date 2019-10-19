plugins {
    id("com.avast.gradle.docker-compose").version("0.9.5")
}

dockerCompose {
    buildBeforeUp = true
    projectName = null
}

tasks.composeUp {
    subprojects.forEach { dependsOn("${it.name}:build") }
}
