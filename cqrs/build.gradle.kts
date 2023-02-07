val kotlinVersion=project.properties["kotlinVersion"]

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("io.micronaut.library")
    id("org.jetbrains.kotlin.kapt")
}

group = "at.fhcampuswien.dev.we"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

micronaut {
    processing {
        incremental(true)
        annotations("at.fhcampuswien.dev.we.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-inject-java:3.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    compileOnly("io.micronaut:micronaut-inject:3.8.0")
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
