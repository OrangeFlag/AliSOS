import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "io.sos"
version = "0.1"

repositories {
    jcenter()
    mavenCentral()
}

tasks.register("stage") {
    this.dependsOn("clean", "shadowJar")
}
tasks.getByPath("shadowJar").mustRunAfter("clean")

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation(platform("io.micronaut:micronaut-bom:1.2.5"))
    kapt(platform("io.micronaut:micronaut-bom:1.2.5"))
    kapt("io.micronaut:micronaut-inject-java:1.2.5")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("org.slf4j:slf4j-api:1.7.28")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("org.jetbrains.exposed:exposed:0.16.1")
    implementation("org.postgresql:postgresql:42.2.6")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

tasks.withType<ShadowJar> {
    manifest.attributes.apply {
        put("Main-Class", "io.sos.alisos.Application")
    }
    mergeServiceFiles()
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}