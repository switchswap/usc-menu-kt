import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask

val kotlinVersion = "1.3.71"
val jsoupVersion = "1.13.1"

plugins {
    // Apply the java-library plugin for API and implementation separation.
    java

    // Apply the maven publish plugin
    maven

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.3.71"

    // Apply Dokka plugin for documentation
    id("org.jetbrains.dokka") version "0.10.0"

}

group = "me.switchswap"
version = "1.0.0"

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(tasks.dokka)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jsoup:jsoup:$jsoupVersion")
}
