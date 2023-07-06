package at.fhcampuswien.dev.we.repository

import at.fhcampuswien.dev.we.domain.aggregates.StationOrder
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface StationOrderRepository : CrudRepository<StationOrder, String> {
    fun getByStationType(stationType: String): List<StationOrder>
    fun findByOrderId(orderId: String): List<StationOrder>
}
