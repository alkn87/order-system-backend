package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.station.StationOrderDto
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient
import io.micronaut.rabbitmq.annotation.RabbitProperty

@RabbitClient("station")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
fun interface StationRPCService {

    @Binding("station.query")
    fun getAllByStation(stationType: String): List<StationOrderDto>
}
