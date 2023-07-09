package at.fhcampuswien.dev.we.domain.event

import at.fhcampuswien.dev.we.cqrs.event.Event
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem

class OrderCreatedEvent(
    val orderId: String,
    val orderItems: List<OrderItem>,
    val deliverTo: String,
    val commentFood: String?,
    val commentDrink: String?
) : Event
