package at.fhcampuswien.dev.we.order.model.station

import io.micronaut.core.annotation.Introspected

@Introspected
data class StationOrderDto(
    var stationOrderItems: List<StationOrderItemDto>,
    var stationType: String,
    var status: String,
    var deliverTo: String,
    var comment: String?,
    val id: String
)
