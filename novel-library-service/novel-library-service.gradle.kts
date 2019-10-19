import com.bmuschko.gradle.docker.tasks.image.Dockerfile

plugins {
    java
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "5.2.0"
    idea
}

group = "de.roamingthings.tracing"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

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
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
    }
}

val testIntegrationImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["testIntegrationRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter:2.0.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
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
        ports.set(listOf(8080))
        tag.set("novel-library-service:latest")
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
        testSourceDirs = testSourceDirs + sourceSets["testIntegration"].java.srcDirs
        testSourceDirs = testSourceDirs + sourceSets["testIntegration"].resources.srcDirs
        testResourceDirs = testResourceDirs + sourceSets["testIntegration"].resources.srcDirs

        isDownloadJavadoc = true
        isDownloadSources = true
    }
}