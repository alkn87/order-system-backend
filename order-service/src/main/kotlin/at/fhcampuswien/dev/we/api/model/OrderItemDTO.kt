package at.fhcampuswien.dev.we.api.model

data class OrderItemDTO(
    val id: String,
    val productId: String,
    val productName: String,
    val unitPrice: Double,
    val quantity: Int
)
