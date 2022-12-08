package at.fhcampuswien.dev.we.domain.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Deserializable
@Serdeable.Serializable
data class StationOrderItem(val productName: String, val quantity: Int)
