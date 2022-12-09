package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.cqrs.query.QueryBus
import at.fhcampuswien.dev.we.domain.command.BlockProductCommand
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.domain.query.GetAllProductsQuery
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus, private val queryBus: QueryBus) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("product-commands")
    fun onReceived(product: ProductDTO) {
        logger.info("product-service - data received: $product")
        commandBus.dispatch(
            CreateProductCommand(product.productName, product.productPrice, product.productType)
        )
    }

    @Queue("product-block")
    fun onReceivedBlock(product: ProductDTO) {
        logger.info("product-service - data received: $product")
        commandBus.dispatch(
            BlockProductCommand(product.productName)
        )
    }

    @Queue("product-queries")
    fun onQueryRpc(data: ByteArray): List<ProductDTO> {
        logger.info("product-queries - rpc call received")
        @Suppress("UNCHECKED_CAST")
        return queryBus.dispatch(GetAllProductsQuery()) as List<ProductDTO>
    }

}
