package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.order.OrderBillingDTO
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient
import io.micronaut.rabbitmq.annotation.RabbitProperty

@RabbitClient("order")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
interface OrderRPCService {

    @Binding("order.query")
    fun getOrders(data: String = "NOP"): List<OrderDTO>

    @Binding("order.query-for-billing")
    fun getOrdersForBilling(data: String = "NOP"): List<OrderBillingDTO>
}
