package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.event.AsyncEventHandler
import at.fhcampuswien.dev.we.domain.event.ProductDeletedEvent
import at.fhcampuswien.dev.we.messaging.MessageProducerService
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class ProductDeletedEventHandler(
    private val messageProducerService: MessageProducerService
) :
    AsyncEventHandler<ProductDeletedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(ProductDeletedEventHandler::class.java)

    @Async
    override fun handle(event: ProductDeletedEvent) {
        messageProducerService.send("UPDATE")
        logger.info("handled ProductUpdatedEvent: $event")
    }
}
