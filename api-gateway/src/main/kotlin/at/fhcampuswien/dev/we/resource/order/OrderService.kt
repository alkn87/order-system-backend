package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.messaging.OrderMessageProducerService
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import jakarta.inject.Singleton


@Singleton
class OrderService(private val messageProducerService: OrderMessageProducerService) {

    fun createOrder(order: OrderDTO): OrderDTO {
        messageProducerService.send(order)
        return order
    }
}
