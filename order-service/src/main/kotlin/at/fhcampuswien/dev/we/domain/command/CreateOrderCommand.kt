package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.api.model.OrderItemDTO
import at.fhcampuswien.dev.we.cqrs.command.Command

class CreateOrderCommand(val deliverTo: String, val stationId: String, val orderItems: List<OrderItemDTO>) : Command
