package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("order")
interface OrderMessageProducerService {
    @Binding("order.command.create")
    fun send(order: OrderDTO)
}
