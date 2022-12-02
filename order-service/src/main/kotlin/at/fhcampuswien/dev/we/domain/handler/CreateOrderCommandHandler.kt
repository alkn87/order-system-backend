package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import at.fhcampuswien.dev.we.repository.OrderRepository
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateOrderCommandHandler(private val orderRepository: OrderRepository) : CommandHandler<CreateOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateOrderCommandHandler::class.java)

    @Async
    override fun handle(command: CreateOrderCommand) {
        val order = Order(command.stationId, command.deliverTo, command.orderItems, OrderStatus.CREATED)
        orderRepository.save(order)
        logger.info("handled CreateOrderCommand: $command")
    }
}
