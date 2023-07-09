package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command
import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO

class CreateStationOrderCommand(
    val orderId: String,
    val orderItems: List<OrderItemDTO>,
    val deliverTo: String,
    val commentFood: String?,
    val commentDrink: String?
) : Command
