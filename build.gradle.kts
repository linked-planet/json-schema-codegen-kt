plugins {
    kotlin("jvm") version "1.5.10"
}

group = "com.linked-planet.tools."
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.code.gson:gson:2.8.8")
}