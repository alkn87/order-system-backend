package at.fhcampuswien.dev.we.integration

import at.fhcampuswien.dev.we.DatabaseBaseTest
import at.fhcampuswien.dev.we.domain.aggregates.Product
import at.fhcampuswien.dev.we.repository.ProductRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Testcontainers

@MicronautTest(transactional = false)
@Testcontainers
class ProductRepositoryTest : DatabaseBaseTest() {

    private val repository: ProductRepository = context.getBean(ProductRepository::class.java)

    @Test
    fun testItWorks() {
        assertTrue(application.isRunning)
    }

    @Test
    fun testShouldSaveProduct() {
        repository.save(Product("Coke", 2.90, "DRINK"))
        assertEquals(1, repository.findAll().count())
        assertTrue(repository.existsByProductName("Coke"))
    }

    @AfterEach
    fun cleanDatabase() {
        repository.deleteAll()
    }
}
