package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.command.DeliverOrderCommand
import at.fhcampuswien.dev.we.messaging.GatewayMessageService
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class DeliverOrderCommandHandler(
    private val repository: OrderRepository,
    private val gatewayMessageService: GatewayMessageService
) : CommandHandler<DeliverOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(DeliverOrderCommandHandler::class.java)

    override fun handle(command: DeliverOrderCommand) {
        repository.findById(command.orderId).ifPresentOrElse(
            {
                it.orderStatus = OrderStatus.DELIVERED
                repository.update(it)
                gatewayMessageService.send("UPDATE")
                logger.info("handled DeliverOrderCommand, order with ID ${it.id} delivered!")
            },
            {
                logger.error("Couldn't DeliverOrderCommand, order with ID ${command.orderId} not found!")
            })
    }

}
