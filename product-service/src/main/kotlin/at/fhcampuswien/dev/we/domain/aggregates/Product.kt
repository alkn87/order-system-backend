package at.fhcampuswien.dev.we.domain.aggregates

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Product (
    var productName: String,
    var productPrice: Double,
    var productType: String,
    var productStatus: String? = "",
    @field:Id
    @GeneratedValue
    var id: String? = ""
)
