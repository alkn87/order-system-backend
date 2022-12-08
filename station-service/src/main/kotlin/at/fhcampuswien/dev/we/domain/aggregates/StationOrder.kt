package at.fhcampuswien.dev.we.domain.aggregates

import at.fhcampuswien.dev.we.domain.model.StationOrderItem
import at.fhcampuswien.dev.we.domain.model.StationOrderStatus
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class StationOrder(
    var orderId: String,
    var stationOrderItems: List<StationOrderItem>,
    var stationType: String,
    var status: StationOrderStatus,
    @field:Id
    @GeneratedValue
    var id: String? = ""
)
