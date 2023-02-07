import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.0" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.micronaut.application") version "3.7.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.0" apply false
    id("io.micronaut.library") version "3.7.0" apply false
}
