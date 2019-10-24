import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

group = "io.sos"
version = "0.1"

repositories {
    jcenter()
    mavenCentral()
}

//mainClassName = "io.sos.alisos.Application"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("io.micronaut:micronaut-bom:1.2.5"))
    kapt("io.micronaut:micronaut-inject-java:1.2.5")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}