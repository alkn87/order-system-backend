package at.fhcampuswien.dev.we.order.model.integration.event

import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO
import io.micronaut.core.annotation.Introspected

@Introspected
data class OrderCreatedIntegrationEvent(
    val orderId: String,
    val orderItems: List<OrderItemDTO>,
    val deliverTo: String,
    val commentFood: String?,
    val commentDrink: String?
)
