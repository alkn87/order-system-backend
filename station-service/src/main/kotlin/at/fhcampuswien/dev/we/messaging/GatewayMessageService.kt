package at.fhcampuswien.dev.we.messaging

import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("gateway")
interface GatewayMessageService {
    @Binding("gateway.command")
    fun send(command: String)
}
