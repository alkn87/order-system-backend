package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.messaging.OrderMessageProducerService
import at.fhcampuswien.dev.we.messaging.OrderRPCService
import at.fhcampuswien.dev.we.messaging.StationMessageService
import at.fhcampuswien.dev.we.messaging.StationRPCService
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import at.fhcampuswien.dev.we.order.model.station.StationOrderDto
import jakarta.inject.Singleton


@Singleton
class OrderService(
    private val messageProducerService: OrderMessageProducerService,
    private val stationRPCService: StationRPCService,
    private val orderRPCService: OrderRPCService,
    private val stationMessageService: StationMessageService
) {

    fun createOrder(order: OrderDTO): OrderDTO {
        messageProducerService.sendCreateCommand(order)
        return order
    }

    fun billOrder(orderId: String): String {
        messageProducerService.sendBillingCommand(orderId)
        return orderId
    }

    fun getOrders(): List<OrderDTO> {
        return orderRPCService.getOrders()
    }

    fun getOrdersByStation(stationType: String): List<StationOrderDto> {
        return stationRPCService.getAllByStation(stationType)
    }

    fun finishStationOrder(stationOrderId: String) {
        stationMessageService.sendFinishCommand(stationOrderId)
    }
}
