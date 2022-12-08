package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.event.AsyncEventHandler
import at.fhcampuswien.dev.we.domain.event.OrderCreatedEvent
import at.fhcampuswien.dev.we.messaging.StationMessageService
import at.fhcampuswien.dev.we.order.model.integration.event.OrderCreatedIntegrationEvent
import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class OrderCreatedEventHandler(private val messageService: StationMessageService) :
    AsyncEventHandler<OrderCreatedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(OrderCreatedEventHandler::class.java)


    @Async
    override fun handle(event: OrderCreatedEvent) {
        messageService.sendEvent(mapToIntegrationEvent(event))
        logger.info("handled OrderCreatedEvent: $event")
    }

    private fun mapToIntegrationEvent(event: OrderCreatedEvent): OrderCreatedIntegrationEvent {
        return OrderCreatedIntegrationEvent(
            event.orderId,
            event.orderItems.map { OrderItemDTO(it.productType, it.productName, it.unitPrice, it.quantity) })
    }
}
