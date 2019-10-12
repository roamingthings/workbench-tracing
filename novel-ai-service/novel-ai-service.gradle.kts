import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
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

val mockitoKotlinVersion: String by extra
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
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

    // Always run
    outputs.upToDateWhen { false }

    shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }
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
