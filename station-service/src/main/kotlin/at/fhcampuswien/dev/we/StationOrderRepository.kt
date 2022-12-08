package at.fhcampuswien.dev.we

import at.fhcampuswien.dev.we.domain.aggregates.StationOrder
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface StationOrderRepository : CrudRepository<StationOrder, String>
