package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.Order
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("order")
interface MessageProducerService {
    @Binding("order.command")
    fun send(order: Order)
}
