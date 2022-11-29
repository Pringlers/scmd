import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

group = "me.hayeon"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.dv8tion:JDA:5.0.0-beta.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
