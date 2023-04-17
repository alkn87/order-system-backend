import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.10" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("io.micronaut.application") version "3.7.8" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.10" apply false
    id("io.micronaut.library") version "3.7.7" apply false
}
