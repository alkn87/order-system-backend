package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.command.FinishOrderCommand
import at.fhcampuswien.dev.we.messaging.GatewayMessageService
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class FinishOrderCommandHandler(
    private val repository: OrderRepository,
    private val gatewayMessageService: GatewayMessageService
) : CommandHandler<FinishOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(FinishOrderCommandHandler::class.java)

    override fun handle(command: FinishOrderCommand) {
        repository.findById(command.orderId).ifPresentOrElse(
            {
                it.orderStatus = OrderStatus.DELIVERED
                repository.update(it)
                gatewayMessageService.send("UPDATE")
                logger.info("handled FinishOrderCommand, order with ID ${it.id} delivered!")
            },
            {
                logger.error("Couldn't FinishOrderCommand, order with ID ${command.orderId} not found!")
            })
    }

}
