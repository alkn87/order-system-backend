package at.fhcampuswien.dev.we

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("at.fhcampuswien.dev.we")
        .start()
}
