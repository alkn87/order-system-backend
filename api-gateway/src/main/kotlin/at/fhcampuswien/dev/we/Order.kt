package at.fhcampuswien.dev.we

import io.micronaut.core.annotation.Introspected

@Introspected
data class Order(val deliverTo: String)
