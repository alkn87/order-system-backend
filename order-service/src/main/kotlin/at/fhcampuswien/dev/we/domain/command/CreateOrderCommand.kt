package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem

class CreateOrderCommand(val deliverTo: String, val orderAgent: String, val orderItems: List<OrderItem>) : Command
