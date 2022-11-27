package at.fhcampuswien.dev.we.order.model.order

data class OrderDTO(
    var stationId: String,
    var deliverTo: String,
    var orderItems: List<OrderItemDTO>
)
