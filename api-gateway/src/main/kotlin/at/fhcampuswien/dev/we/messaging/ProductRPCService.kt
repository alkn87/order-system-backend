package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient
import io.micronaut.rabbitmq.annotation.RabbitProperty

@RabbitClient("product")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
interface ProductRPCService {

    @Binding("product.query")
    fun getAll(data: ByteArray): List<ProductDTO>
}
