package at.fhcampuswien.dev.we.domain.aggregates

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Deserializable
@Serdeable.Serializable
data class OrderItem(
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int
)
