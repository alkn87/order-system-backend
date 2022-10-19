package at.fhcampuswien.dev.we.messaging

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton

@Singleton
class ChannelPoolListener : ChannelInitializer() {

    override fun initialize(channel: Channel, name: String) {

        //docs/exchange
        channel.exchangeDeclare("order", "topic", false)
        channel.queueDeclare("order-commands", false, false, false, null)
        channel.queueDeclare("order-queries", false, false, false, null)
        channel.queueBind("order-commands", "order", "order.command")
        channel.queueBind("order-queries", "order", "order.query")
    }
}
