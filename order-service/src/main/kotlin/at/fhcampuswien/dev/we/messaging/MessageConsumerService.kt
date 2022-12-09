package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.cqrs.query.QueryBus
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem
import at.fhcampuswien.dev.we.domain.command.BillOrderCommand
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import at.fhcampuswien.dev.we.domain.command.DeliverOrderCommand
import at.fhcampuswien.dev.we.domain.query.GetOrderQuery
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus, private val queryBus: QueryBus) {

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

    @Queue("order-commands-bill")
    fun onBillReceived(orderId: String) {
        logger.info("order-commands-bill - data received: $orderId")
        commandBus.dispatch(BillOrderCommand(orderId))
    }

    @Queue("order-commands-update")
    fun onReceivedUpdate(orderId: String) {
        logger.info("order-commands-update not yet implemented!")
    }

    @Queue("order-commands-deliver")
    fun onReceivedDeliver(orderId: String) {
        commandBus.dispatch(DeliverOrderCommand(orderId))
        logger.info("order-service - deliver message for Order $orderId received!")
    }

    @Queue("order-queries")
    fun onQueryRpc(stationType: String): List<OrderDTO> {
        logger.info("order-queries - rpc call received")
        @Suppress("UNCHECKED_CAST")
        return queryBus.dispatch(GetOrderQuery()) as List<OrderDTO>
    }
}
