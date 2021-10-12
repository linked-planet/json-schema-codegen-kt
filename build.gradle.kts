import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.10"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
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

application {
    mainClassName =  "com.linkedplanet.tools.jsonschemakt.Application"
}

tasks.withType<ShadowJar> {
    archiveBaseName.set(project.name)
    archiveClassifier.set("all")
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer::class.java) {
        resource = "reference.conf"
    }
}
