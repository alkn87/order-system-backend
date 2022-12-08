package at.fhcampuswien.dev.we.messaging

import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("station")
interface StationMessageService {
    @Binding("station.command.finish")
    fun sendFinishCommand(sendFinishCommand: String)
}
