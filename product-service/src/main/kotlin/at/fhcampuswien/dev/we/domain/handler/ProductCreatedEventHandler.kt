package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.event.AsyncEventHandler
import at.fhcampuswien.dev.we.domain.event.ProductCreatedEvent
import at.fhcampuswien.dev.we.messaging.MessageProducerService
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class ProductCreatedEventHandler(
    private val messageProducerService: MessageProducerService
) :
    AsyncEventHandler<ProductCreatedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(ProductCreatedEventHandler::class.java)

    @Async
    override fun handle(event: ProductCreatedEvent) {
        messageProducerService.send("UPDATE")
        logger.info("handled ProductCreatedEvent: $event")
    }
}
