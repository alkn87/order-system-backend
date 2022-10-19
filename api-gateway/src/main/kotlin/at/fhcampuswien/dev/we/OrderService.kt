package at.fhcampuswien.dev.we

import at.fhcampuswien.dev.we.messaging.MessageProducerService
import jakarta.inject.Singleton
import java.util.*


@Singleton
class OrderService(val messageProducerService: MessageProducerService) {

    companion object {
        private val Orders = listOf(
            Order("T1"),
            Order("T2"),
            Order("T3"),
            Order("T4"),
            Order("T5"),
            Order("T6")
        )
    }

    fun createOrder(): Order {
        val order = randomOrder()
        messageProducerService.send(order)
        return order
    }

    private fun randomOrder(): Order = Orders[Random().nextInt(Orders.size)]

}
