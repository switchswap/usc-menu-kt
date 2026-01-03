plugins {
    // Apply the java-library plugin for API and implementation separation.
    java

    // Apply the maven publish plugin
    id("maven-publish")

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "2.3.0"

    // Apply Dokka plugin for documentation
    id("org.jetbrains.dokka") version "1.9.20"

}

group = "me.switchswap"
version = "2.0.0"


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
    implementation("com.squareup.okhttp3:okhttp:5.3.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("com.google.truth:truth:1.4.5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()

    maxHeapSize = "1G"

    testLogging {
        events("passed")
    }
}
