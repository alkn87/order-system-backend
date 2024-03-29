package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.integration.command.ProductIntegrationCommand
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("product")
interface ProductMessageProducerService {
    @Binding("product.command")
    fun send(productIntegrationCommand: ProductIntegrationCommand)

    @Binding("product.block")
    fun sendBlock(product: ProductDTO)
}
