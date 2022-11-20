package at.fhcampuswien.dev.we.domain.aggregates

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Order (
    var stationId: String,
    var deliverTo: String,
    var orderItems: List<OrderItem>,
    var orderStatus: OrderStatus = OrderStatus.CREATED,
    @field:Id
    @GeneratedValue
    var id: String? = ""
    )

@Introspected
data class TestOrder (
    var name: String,
    var description: String)
