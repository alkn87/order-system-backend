package at.fhcampuswien.dev.we.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Order (
    var name: String,
    var description: String,
    @field:Id
    @GeneratedValue
    var id: String? = ""
    )

@Introspected
data class TestOrder (
    var name: String,
    var description: String)
