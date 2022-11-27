package at.fhcampuswien.dev.we.messaging

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton

@Singleton
class ChannelPoolListener : ChannelInitializer() {

    override fun initialize(channel: Channel, name: String) {

        channel.exchangeDeclare("gateway", "topic", false)
        channel.queueDeclare("gateway-commands", false, false, false, null)
        channel.queueBind("gateway-commands", "gateway", "gateway.command")
    }
}
