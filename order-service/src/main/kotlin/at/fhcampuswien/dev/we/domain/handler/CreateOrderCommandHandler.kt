package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.cqrs.event.EventBus
import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import at.fhcampuswien.dev.we.domain.event.OrderCreatedEvent
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateOrderCommandHandler(private val orderRepository: OrderRepository, private val eventBus: EventBus) :
    CommandHandler<CreateOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateOrderCommandHandler::class.java)

    override fun handle(command: CreateOrderCommand) {
        val order = Order(command.orderAgent, command.deliverTo, command.orderItems, OrderStatus.CREATED)
        val savedOrder = orderRepository.save(order)
        if (savedOrder.id.isNullOrEmpty()) {
            logger.error("When handling command: $command - entity not persisted successfully!")
        } else {
            eventBus.dispatchAsync(OrderCreatedEvent(savedOrder.id!!, savedOrder.orderItems))
            logger.info("handled CreateOrderCommand: $command")
        }

    }
}
