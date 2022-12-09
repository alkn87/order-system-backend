package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.command.BlockProductCommand
import at.fhcampuswien.dev.we.messaging.MessageProducerService
import at.fhcampuswien.dev.we.repository.ProductRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class BlockProductCommandHandler(
    private val repository: ProductRepository,
    private val messageProducerService: MessageProducerService
) : CommandHandler<BlockProductCommand> {

    private val logger: Logger = LoggerFactory.getLogger(BlockProductCommandHandler::class.java)

    override fun handle(command: BlockProductCommand) {

            val product = repository.findByProductName(command.productName)
            if (product != null) {
                if (product.productStatus == "BLOCKED") {
                    product.productStatus = "AVAILABLE"
                } else {
                    product.productStatus = "BLOCKED"
                }
                repository.update(product)
                messageProducerService.send("UPDATE")
                logger.info("handled BlockProductCommand: $command")
            } else {
                logger.error("Couldn't handle BlockProductCommand: $command")
            }
    }
}
