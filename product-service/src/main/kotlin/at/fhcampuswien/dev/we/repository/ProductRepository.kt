package at.fhcampuswien.dev.we.repository

import at.fhcampuswien.dev.we.domain.aggregates.Product
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@MongoRepository
interface ProductRepository : CrudRepository<Product, String> {
    fun findByProductName(productName: String): Product?

    fun existsByProductName(productName: String): Boolean
}
