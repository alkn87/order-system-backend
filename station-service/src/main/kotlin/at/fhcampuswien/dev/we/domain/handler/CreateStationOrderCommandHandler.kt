package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.repository.StationOrderRepository
import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.aggregates.StationOrder
import at.fhcampuswien.dev.we.domain.command.CreateStationOrderCommand
import at.fhcampuswien.dev.we.domain.model.StationOrderItem
import at.fhcampuswien.dev.we.domain.model.StationOrderStatus
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateStationOrderCommandHandler(private val repository: StationOrderRepository) :
    CommandHandler<CreateStationOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateStationOrderCommandHandler::class.java)

    override fun handle(command: CreateStationOrderCommand) {
        val groupedOrderItems = command.orderItems.groupBy { it.productType }
        groupedOrderItems.forEach {
            run {
                val stationOrder = StationOrder(
                    command.orderId,
                    it.value.map { item -> StationOrderItem(item.productName, item.quantity) },
                    it.key,
                    StationOrderStatus.CREATED,
                    command.deliverTo
                )
                repository.save(stationOrder)
            }
        }
        val savedStationOrders = repository.findAll()
        logger.info(savedStationOrders.toList().toString())
    }
}
