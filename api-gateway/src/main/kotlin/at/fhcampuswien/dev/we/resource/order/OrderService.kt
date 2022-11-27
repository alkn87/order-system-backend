package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.messaging.OrderMessageProducerService
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO
import jakarta.inject.Singleton


@Singleton
class OrderService(private val messageProducerService: OrderMessageProducerService) {

    fun createOrder(): OrderDTO {
        val order = OrderDTO(
            "station1",
            "Table 1",
            listOf(
                OrderItemDTO("F1", "Burger", 5.90, 2),
                OrderItemDTO("D1", "Coke", 2.90, 3)

            ))
        messageProducerService.send(order)
        return order
    }
}
