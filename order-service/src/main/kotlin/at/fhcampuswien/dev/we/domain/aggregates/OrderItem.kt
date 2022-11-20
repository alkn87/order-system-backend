package at.fhcampuswien.dev.we.domain.aggregates

data class OrderItem(
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int
)
