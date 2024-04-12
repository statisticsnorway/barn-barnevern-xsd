repositories { mavenCentral() }

plugins {
    kotlin("jvm") version libs.versions.kotlin
    `maven-publish`
    id("com.google.cloud.artifactregistry.gradle-plugin") version "2.2.1"
    id("org.sonarqube") version "5.0.0.4638"
    jacoco
}

kotlin { jvmToolchain(17) }

dependencies {
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.jakarta.rs.xml.provider)
    implementation(libs.jackson.datatype.jsr310)
    api(libs.jackson.dataformat.xml)
    api(libs.jakarta.xml.bind.api)

    testImplementation(libs.kotest.runner.junit5.jvm)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "no.ssb.barn"
            artifactId = rootProject.name
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
    repositories {
        maven("artifactregistry://europe-north1-maven.pkg.dev/artifact-registry-14da/maven-releases")
    }
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<JacocoReport> {
    dependsOn(tasks.withType<Test>())
    reports { xml.required = true }
}

sonarqube {
    properties {
        property("sonar.organization", "statisticsnorway")
        property("sonar.projectKey", "statisticsnorway_barn-barnevern-xsd")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/xsd/*Type.kt")
    }
}
