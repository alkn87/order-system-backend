package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.query.QueryHandler
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.query.GetOrderForBillingQuery
import at.fhcampuswien.dev.we.order.model.order.OrderBillingDTO
import at.fhcampuswien.dev.we.order.model.order.OrderItemDTO
import at.fhcampuswien.dev.we.repository.OrderRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class GetOrderForBillingQueryHandler(private val repository: OrderRepository) : QueryHandler<GetOrderForBillingQuery, List<OrderBillingDTO>> {

    private val logger: Logger = LoggerFactory.getLogger(GetOrderForBillingQueryHandler::class.java)

    override fun handle(query: GetOrderForBillingQuery): List<OrderBillingDTO> {
        logger.info("handling query to get all orders for billing!")
        return repository.findByOrderStatus(OrderStatus.DELIVERED).toList().groupBy { it.deliverTo }.map { (deliverTo, orders) ->
            OrderBillingDTO(
                deliverTo,
                orders.flatMap { order ->
                    order.orderItems.map { item ->
                        OrderItemDTO(
                            item.productType,
                            item.productName,
                            item.unitPrice,
                            item.quantity
                        )
                    }
                }
            )
        }
    }
}
