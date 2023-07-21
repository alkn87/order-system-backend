package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.event.AsyncEventHandler
import at.fhcampuswien.dev.we.domain.event.ProductUpdatedEvent
import at.fhcampuswien.dev.we.messaging.MessageProducerService
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class ProductUpdatedEventHandler(
    private val messageProducerService: MessageProducerService
) :
    AsyncEventHandler<ProductUpdatedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(ProductUpdatedEventHandler::class.java)

    @Async
    override fun handle(event: ProductUpdatedEvent) {
        messageProducerService.send("UPDATE")
        logger.info("handled ProductUpdatedEvent: $event")
    }
}
