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
    version.set("3.9.2")
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
        exclude(group = "org.junit", module = "junit-jupiter-api")
        exclude(group = "org.junit", module = "junit-jupiter-engine")
    }
    implementation(project(":common")) {
        exclude(group = "org.junit", module = "junit-jupiter-api")
        exclude(group = "org.junit", module = "junit-jupiter-engine")
    }

    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")

    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("javax.inject:javax.inject:1")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.rabbitmq:micronaut-rabbitmq")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.micronaut.reactor:micronaut-reactor")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.graalvm.nativeimage:svm")
    testImplementation("org.mockito:mockito-core:5.3.0")

    implementation("org.slf4j:slf4j-simple")

}


application {
    mainClass.set("at.fhcampuswien.dev.we.ApplicationKt")
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "17"
    }
}
