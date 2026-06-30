description = "XSD-prosjekt for innrapportering til Barnevernsregister"
val barnevernMaven = "artifactregistry://europe-north1-maven.pkg.dev/artifact-registry-5n/barnevern-maven"

repositories { mavenCentral() }

plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    `maven-publish`
    id("com.google.cloud.artifactregistry.gradle-plugin") version "2.2.5"
    id("org.sonarqube") version "7.2.1.6560"
    jacoco
}

kotlin { jvmToolchain(21) }

val xjc by configurations.creating

dependencies {
    api(libs.jackson.module.kotlin)
    api(libs.jackson.jakarta.rs.xml.provider)
    api(libs.jackson.datatype.jsr310)

    testImplementation(libs.kotest.runner.junit5.jvm)

    xjc("org.glassfish.jaxb:jaxb-xjc:4.0.5")
    xjc("org.glassfish.jaxb:jaxb-runtime:4.0.5")
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
    repositories { maven(barnevernMaven) }
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

tasks.register<XjcTask>("generateXsd")
