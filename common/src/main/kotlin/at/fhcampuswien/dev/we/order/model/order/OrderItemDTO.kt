package at.fhcampuswien.dev.we.order.model.order

import io.micronaut.core.annotation.Introspected

@Introspected
data class OrderItemDTO(
    val productType: String,
    val productName: String,
    val productPrice: Double,
    val quantity: Int
)
