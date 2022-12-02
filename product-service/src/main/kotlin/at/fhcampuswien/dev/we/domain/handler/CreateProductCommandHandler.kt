package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.Product
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.repository.ProductRepository
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateProductCommandHandler(private val repository: ProductRepository) : CommandHandler<CreateProductCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateProductCommandHandler::class.java)

    @Async
    override fun handle(command: CreateProductCommand) {
        val product = Product(command.productName, command.productPrice, command.productType)
        if (!repository.existsByProductName(command.productName)) {
            repository.save(product)
        }
        logger.info("handled CreateProductCommand: $command")
    }
}
