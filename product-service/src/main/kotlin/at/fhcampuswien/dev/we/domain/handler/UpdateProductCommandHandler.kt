package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.cqrs.event.EventBus
import at.fhcampuswien.dev.we.domain.aggregates.Product
import at.fhcampuswien.dev.we.domain.command.UpdateProductCommand
import at.fhcampuswien.dev.we.domain.event.ProductUpdatedEvent
import at.fhcampuswien.dev.we.repository.ProductRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class UpdateProductCommandHandler(private val repository: ProductRepository, private val eventBus: EventBus) :
    CommandHandler<UpdateProductCommand> {

    private val logger: Logger = LoggerFactory.getLogger(UpdateProductCommandHandler::class.java)

    override fun handle(command: UpdateProductCommand) {
        val updatedProduct = repository.update(
            Product(
                productName = command.productDTO.productName,
                productPrice = command.productDTO.productPrice,
                productType = command.productDTO.productType,
                productStatus = command.productDTO.productStatus,
                id = command.productDTO.id
            )
        )
        logger.info("handled UpdateProductCommand: ${command.productDTO}")
        eventBus.dispatchAsync(ProductUpdatedEvent(updatedProduct.productName))
    }
}
