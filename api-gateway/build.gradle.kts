val kotlinVersion= project.properties["kotlinVersion"]

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("org.jetbrains.kotlin.kapt")
}

version = "0.1"
group = "at.fhcampuswien.dev.we"


repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("at.fhcampuswien.dev.we.*")
    }
}

dependencies {
    // shared lib
    implementation(project(":cqrs")) {
        exclude(group = "org.junit", module="junit-jupiter-api")
        exclude(group = "org.junit", module="junit-jupiter-engine")
    }
    implementation(project(":common")) {
        exclude(group = "org.junit", module="junit-jupiter-api")
        exclude(group = "org.junit", module="junit-jupiter-engine")
    }

    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")

    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("javax.inject:javax.inject:1")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.rabbitmq:micronaut-rabbitmq")
    implementation("com.google.code.gson:gson:2.10")
    implementation("io.micronaut.reactor:micronaut-reactor")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.graalvm.nativeimage:svm")
    testImplementation("org.mockito:mockito-core:5.0.0")

}


application {
    mainClass.set("at.fhcampuswien.dev.we.ApplicationKt")
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
