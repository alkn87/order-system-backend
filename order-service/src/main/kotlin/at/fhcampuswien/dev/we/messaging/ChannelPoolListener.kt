package at.fhcampuswien.dev.we.messaging

import com.rabbitmq.client.Channel
import io.micronaut.rabbitmq.connect.ChannelInitializer
import jakarta.inject.Singleton

@Singleton
class ChannelPoolListener : ChannelInitializer() {

    override fun initialize(channel: Channel, name: String) {

        //docs/exchange
        channel.exchangeDeclare("order", "topic", false)
        channel.queueDeclare("order-commands-create", false, false, false, null)
        channel.queueDeclare("order-commands-update", false, false, false, null)
        channel.queueDeclare("order-commands-deliver", false, false, false, null)
        channel.queueDeclare("order-commands-bill", false, false, false, null)
        channel.queueDeclare("order-queries", false, false, false, null)
        channel.queueDeclare("order-queries-for-billing", false, false, false, null)
        channel.queueBind("order-commands-create", "order", "order.command.create")
        channel.queueBind("order-commands-update", "order", "order.command.update")
        channel.queueBind("order-commands-deliver", "order", "order.command.deliver")
        channel.queueBind("order-commands-bill", "order", "order.command.bill")
        channel.queueBind("order-queries", "order", "order.query")
        channel.queueBind("order-queries-for-billing", "order", "order.query-for-billing")
    }
}
