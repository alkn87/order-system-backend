package at.fhcampuswien.dev.we.repository

import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface OrderRepository : CrudRepository<Order, String> {
    fun findByOrderStatus(orderStatus: OrderStatus): List<Order>
    fun findByDeliverToAndOrderStatus(deliverTo: String, orderStatus: OrderStatus): List<Order>
}
