package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("product-commands")
    fun onReceived(product: ProductDTO) {
        logger.info("product-service - data received: $product")
        commandBus.dispatch(
            CreateProductCommand(product.productName, product.productPrice, product.productType)
//            CreateProductCommand(
//                "Coke",
//                2.50,
//                "DRINK"
//            )
        )
    }
}
