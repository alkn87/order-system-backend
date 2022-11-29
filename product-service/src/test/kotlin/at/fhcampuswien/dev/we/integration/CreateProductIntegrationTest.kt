package at.fhcampuswien.dev.we.integration

import at.fhcampuswien.dev.we.DatabaseBaseTest
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.domain.handler.CreateProductCommandHandler
import at.fhcampuswien.dev.we.repository.ProductRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest(transactional = false)
class CreateProductIntegrationTest : DatabaseBaseTest() {

    private val repository: ProductRepository = context.getBean(ProductRepository::class.java)
    private val createProductCommandHandler: CreateProductCommandHandler = context.getBean(CreateProductCommandHandler::class.java)

    @Test
    fun testShouldNotSaveProductWhenProductNameExists() {

        createProductCommandHandler.handle(CreateProductCommand(
            "Coke",
            2.50,
            "DRINK"
        ))

        createProductCommandHandler.handle(CreateProductCommand(
            "Coke",
            2.90,
            "DRINK"
        ))

        assertEquals(1, repository.findAll().count())
        assertEquals(2.50, repository.findAll().first().productPrice)
    }
}
