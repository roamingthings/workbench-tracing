import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("de.roamingthings.kotlinspring")
    id("com.bmuschko.docker-spring-boot-application") version "5.2.0"
    idea
}

group = "de.roamingthings.tracing"
version = "0.0.1-SNAPSHOT"

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

sourceSets {
    create("testIntegration") {
        java.srcDirs("src/integration-test/java")
        resources.srcDirs("src/integration-test/resources")
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDirs("src/integration-test/kotlin")
        }
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
    }
}

val testIntegrationImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["testIntegrationRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val jaegerCloudStarterVersion: String by project
val jaegerClientVersion: String by project
dependencies {
    implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter:$jaegerClientVersion")
    // Due to compatibility issues with Spring Boot 2.2.0 include a newer version
    implementation("io.opentracing.contrib:opentracing-spring-cloud-starter:$jaegerCloudStarterVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}

tasks.compileJava { dependsOn(tasks.processResources) }

tasks.test {
    useJUnitPlatform()

    systemProperty("spring.profiles.active", "test")

    testLogging {
        events("passed", "skipped", "failed")
    }
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    useJUnitPlatform()

    systemProperty("spring.profiles.active", "integrationtest")

    testClassesDirs = sourceSets["testIntegration"].output.classesDirs
    classpath = sourceSets["testIntegration"].runtimeClasspath

    testLogging {
        events("passed", "skipped", "failed")
    }

    shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }

docker {
    springBootApplication {
        baseImage.set("openjdk:11")
        ports.set(listOf(8080, 5005))
        tag.set("text-library-service:latest")
        jvmArgs.set(listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"))
    }
}

tasks {
    "dockerCreateDockerfile"(Dockerfile::class) {
        instruction("HEALTHCHECK CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1")
    }
}

tasks.build { dependsOn(tasks.dockerBuildImage) }

idea {
    module {
        testSourceDirs = testSourceDirs + sourceSets["testIntegration"].withConvention(KotlinSourceSet::class) {
            kotlin.srcDirs
        }
        testSourceDirs = testSourceDirs + sourceSets["testIntegration"].resources.srcDirs
        testResourceDirs = testResourceDirs + sourceSets["testIntegration"].resources.srcDirs

        isDownloadJavadoc = true
        isDownloadSources = true
    }
}
