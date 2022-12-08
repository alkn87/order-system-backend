package at.fhcampuswien.dev.we.order.model.station

import io.micronaut.core.annotation.Introspected

@Introspected
data class StationOrderItemDto(val productName: String, val quantity: Int)
