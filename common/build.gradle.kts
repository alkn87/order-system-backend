val kotlinVersion = project.properties["kotlinVersion"]

plugins {
    id("io.micronaut.library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
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
    annotationProcessor("io.micronaut:micronaut-inject-java:3.8.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.micronaut:micronaut-validation:3.8.3")

    implementation("org.slf4j:slf4j-simple")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "17"
    }
}
