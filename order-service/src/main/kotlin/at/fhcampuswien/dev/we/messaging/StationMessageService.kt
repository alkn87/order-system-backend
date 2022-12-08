package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.integration.event.OrderCreatedIntegrationEvent
import io.micronaut.rabbitmq.annotation.Binding
import io.micronaut.rabbitmq.annotation.RabbitClient

@RabbitClient("station")
interface StationMessageService {
    @Binding("station.event")
    fun sendEvent(event: OrderCreatedIntegrationEvent)
}
