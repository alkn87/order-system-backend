package at.fhcampuswien.dev.we.order

data class OrderItemDTO(
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int
)
