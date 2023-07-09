package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.query.QueryHandler
import at.fhcampuswien.dev.we.domain.query.GetOrderQuery
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class GetOrderQueryHandler(private val repository: OrderRepository) : QueryHandler<GetOrderQuery, List<OrderDTO>> {

    private val logger: Logger = LoggerFactory.getLogger(GetOrderQueryHandler::class.java)

    override fun handle(query: GetOrderQuery): List<OrderDTO> {
        logger.info("handling query to get all orders!")
        return repository.findAll().toList().map {
            OrderDTO(
                it.orderAgent,
                it.deliverTo,
                it.orderItems.map { item ->
                    OrderItemDTO(
                        item.productType,
                        item.productName,
                        item.unitPrice,
                        item.quantity
                    )
                },
                it.orderStatus.name,
                null,
                null,
                it.id
            )
        }
    }
}
