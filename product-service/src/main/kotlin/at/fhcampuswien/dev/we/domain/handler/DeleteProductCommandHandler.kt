package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.cqrs.event.EventBus
import at.fhcampuswien.dev.we.domain.aggregates.Product
import at.fhcampuswien.dev.we.domain.command.DeleteProductCommand
import at.fhcampuswien.dev.we.domain.event.ProductDeletedEvent
import at.fhcampuswien.dev.we.repository.ProductRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class DeleteProductCommandHandler(private val repository: ProductRepository, private val eventBus: EventBus) :
    CommandHandler<DeleteProductCommand> {

    private val logger: Logger = LoggerFactory.getLogger(DeleteProductCommandHandler::class.java)

    override fun handle(command: DeleteProductCommand) {
        repository.delete(
            Product(
                productName = command.productDTO.productName,
                productPrice = command.productDTO.productPrice,
                productType = command.productDTO.productType,
                productStatus = command.productDTO.productStatus,
                id = command.productDTO.id
            )
        )
        logger.info("handled DeletedProductCommand: ${command.productDTO}")
        eventBus.dispatchAsync(ProductDeletedEvent(command.productDTO.productName))
    }
}
