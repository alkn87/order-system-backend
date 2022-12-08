package at.fhcampuswien.dev.we.messaging

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton

@Singleton
class ChannelPoolListener : ChannelInitializer() {

    override fun initialize(channel: Channel, name: String) {

        //docs/exchange
        channel.exchangeDeclare("station", "topic", false)
        channel.queueDeclare("station-commands", false, false, false, null)
        channel.queueDeclare("station-queries", false, false, false, null)
        channel.queueDeclare("station-events", false, false, false, null)
        channel.queueBind("station-commands", "station", "station.command")
        channel.queueBind("station-queries", "station", "station.query")
        channel.queueBind("station-events", "station", "station.event")
    }
}
