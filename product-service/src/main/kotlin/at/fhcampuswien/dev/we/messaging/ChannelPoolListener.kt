package at.fhcampuswien.dev.we.messaging

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton

@Singleton
class ChannelPoolListener : ChannelInitializer() {

    override fun initialize(channel: Channel, name: String) {

        //docs/exchange
        channel.exchangeDeclare("product", "topic", false)
        channel.queueDeclare("product-commands", false, false, false, null)
        channel.queueDeclare("product-block", false, false, false, null)
        channel.queueDeclare("product-queries", false, false, false, null)
        channel.queueBind("product-commands", "product", "product.command")
        channel.queueBind("product-block", "product", "product.block")
        channel.queueBind("product-queries", "product", "product.query")
    }
}
