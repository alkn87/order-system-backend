package at.fhcampuswien.dev.we.order

data class OrderDTO(
    var stationId: String,
    var deliverTo: String,
    var orderItems: List<OrderItemDTO>
)
