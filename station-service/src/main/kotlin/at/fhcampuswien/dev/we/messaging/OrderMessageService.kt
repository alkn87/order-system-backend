package at.fhcampuswien.dev.we.messaging

import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("order")
interface OrderMessageService {
    @Binding("order.command.update")
    fun sendOrderUpdate(orderId: String)

    @Binding("order.command.deliver")
    fun sendOrderDelivered(orderId: String)
}
