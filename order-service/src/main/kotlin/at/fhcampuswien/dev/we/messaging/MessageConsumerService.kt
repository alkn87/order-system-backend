package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import at.fhcampuswien.dev.we.domain.command.FinishOrderCommand
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("order-commands-create")
    fun onReceived(order: OrderDTO) {
        logger.info("order-service - data received: $order")
        commandBus.dispatch(
            CreateOrderCommand(
                order.deliverTo,
                order.orderAgent,
                order.orderItems.map {
                    OrderItem(it.productType, it.productName, it.productPrice, it.quantity)
                }
            )
        )
    }

    @Queue("order-commands-update")
    fun onReceivedUpdate(orderId: String) {
        logger.info("order-commands-update not yet implemented!")
    }

    @Queue("order-commands-finish")
    fun onReceivedFinish(orderId: String) {
        commandBus.dispatch(FinishOrderCommand(orderId))
        logger.info("order-service - finish message for Order $orderId received!")
    }
}
