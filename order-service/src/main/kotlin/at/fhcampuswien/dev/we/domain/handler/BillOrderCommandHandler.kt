package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.command.BillOrderCommand
import at.fhcampuswien.dev.we.messaging.GatewayMessageService
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class BillOrderCommandHandler(
    private val repository: OrderRepository,
    private val gatewayMessageService: GatewayMessageService
) : CommandHandler<BillOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(BillOrderCommandHandler::class.java)

    override fun handle(command: BillOrderCommand) {
        repository.findById(command.orderId).ifPresentOrElse(
            {
                it.orderStatus = OrderStatus.FINISHED
                repository.update(it)
                gatewayMessageService.send("UPDATE")
                logger.info("Handled BillOrderCommand - Order with ID ${it.id} set ${it.orderStatus.name}")
            },
            {
                logger.error("Couldn't handle BillOrderCommand - entity with ID ${command.orderId} not found!")
            })
    }
}
