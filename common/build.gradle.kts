val kotlinVersion=project.properties["kotlinVersion"]

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.kapt")
}

group = "at.fhcampuswien.dev.we"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
