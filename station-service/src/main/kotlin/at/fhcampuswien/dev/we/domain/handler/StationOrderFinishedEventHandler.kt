package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.repository.StationOrderRepository
import at.fhcampuswien.dev.we.cqrs.event.AsyncEventHandler
import at.fhcampuswien.dev.we.domain.event.StationOrderFinishedEvent
import at.fhcampuswien.dev.we.domain.model.StationOrderStatus
import at.fhcampuswien.dev.we.messaging.GatewayMessageService
import at.fhcampuswien.dev.we.messaging.OrderMessageService
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class StationOrderFinishedEventHandler(
    private val orderMessageService: OrderMessageService,
    private val repository: StationOrderRepository,
    private val gatewayMessageService: GatewayMessageService
) : AsyncEventHandler<StationOrderFinishedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(StationOrderFinishedEventHandler::class.java)

    @Async
    override fun handle(event: StationOrderFinishedEvent) {
        val stationOrderPerOrder = repository.findByOrderId(event.orderId)
        val statusPerOrder = stationOrderPerOrder.groupBy { it.status }.keys
        logger.info(stationOrderPerOrder.toString())
        logger.info(statusPerOrder.toString())
        if (statusPerOrder.size > 1) {
            orderMessageService.sendOrderUpdate(event.orderId)
            gatewayMessageService.send("UPDATE")
            logger.info("Handled StationOrderFinishedEvent - $event - Order still in progress!")
        } else if (statusPerOrder.size == 1 && statusPerOrder.first() == StationOrderStatus.FINISHED) {
            orderMessageService.sendOrderDelivered(event.orderId)
            logger.info("Handled StationOrderFinishedEvent - $event - Order now finished!")
        }
    }
}
