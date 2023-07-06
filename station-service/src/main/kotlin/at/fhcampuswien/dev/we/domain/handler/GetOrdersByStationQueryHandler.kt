package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.repository.StationOrderRepository
import at.fhcampuswien.dev.we.cqrs.query.QueryHandler
import at.fhcampuswien.dev.we.domain.query.GetOrdersByStationQuery
import at.fhcampuswien.dev.we.order.model.station.StationOrderDto
import at.fhcampuswien.dev.we.order.model.station.StationOrderItemDto
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class GetOrdersByStationQueryHandler(private val repository: StationOrderRepository) :
    QueryHandler<GetOrdersByStationQuery, List<StationOrderDto>> {

    private val logger: Logger = LoggerFactory.getLogger(GetOrdersByStationQueryHandler::class.java)

    override fun handle(query: GetOrdersByStationQuery): List<StationOrderDto> {
        val stationOrders = repository.getByStationType(query.stationType).filter { !it.id.isNullOrEmpty() }.map {
                StationOrderDto(
                    it.stationOrderItems.map { item -> StationOrderItemDto(item.productName, item.quantity) },
                    it.stationType,
                    it.status.name,
                    it.deliverTo,
                    it.id!!
                )
        }
        logger.info(stationOrders.toString())
        return stationOrders
    }
}
