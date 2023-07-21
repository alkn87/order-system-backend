package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.cqrs.query.QueryBus
import at.fhcampuswien.dev.we.domain.command.BlockProductCommand
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.domain.command.DeleteProductCommand
import at.fhcampuswien.dev.we.domain.command.UpdateProductCommand
import at.fhcampuswien.dev.we.domain.query.GetAllProductsQuery
import at.fhcampuswien.dev.we.order.model.integration.command.ProductCommandType
import at.fhcampuswien.dev.we.order.model.integration.command.ProductIntegrationCommand
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
    fun onReceived(productIntegrationCommand: ProductIntegrationCommand) {
        logger.info("product-service - data received: ${productIntegrationCommand.productDTO}")
        // switch case on productIntegrationCommand.commandType
        when (productIntegrationCommand.commandType) {
            ProductCommandType.CREATE -> {
                commandBus.dispatch(
                    CreateProductCommand(
                        productName = productIntegrationCommand.productDTO.productName,
                        productPrice = productIntegrationCommand.productDTO.productPrice,
                        productType = productIntegrationCommand.productDTO.productType
                    )
                )
            }
            ProductCommandType.UPDATE -> {
                commandBus.dispatch(
                    UpdateProductCommand(productIntegrationCommand.productDTO)
                )
            }
            ProductCommandType.DELETE -> {
                commandBus.dispatch(
                    DeleteProductCommand(productIntegrationCommand.productDTO)
                )
            }

            else -> {
                logger.error("product-service - unknown command type")}
        }
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
