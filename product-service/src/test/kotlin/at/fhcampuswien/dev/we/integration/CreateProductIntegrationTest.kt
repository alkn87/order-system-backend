package at.fhcampuswien.dev.we.integration

import at.fhcampuswien.dev.we.DatabaseBaseTest
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import at.fhcampuswien.dev.we.domain.handler.CreateProductCommandHandler
import at.fhcampuswien.dev.we.repository.ProductRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@MicronautTest(transactional = false)
class CreateProductIntegrationTest : DatabaseBaseTest() {

    private val repository: ProductRepository = context.getBean(ProductRepository::class.java)
    private val createProductCommandHandler: CreateProductCommandHandler =
        context.getBean(CreateProductCommandHandler::class.java)

    @Test
    fun testShouldNotSaveProductWhenProductNameExists() {

        val spyObject = spy(createProductCommandHandler)

        val firstCreateProductCommand = CreateProductCommand(
            "Coke",
            2.50,
            "DRINK"
        )

        val secondCreateProductCommand = CreateProductCommand(
            "Coke",
            2.90,
            "DRINK"
        )

        var latch = CountDownLatch(1)
        doAnswer {
            latch.countDown()
        }.`when`(spyObject).handle(firstCreateProductCommand)
        createProductCommandHandler.handle(firstCreateProductCommand)
        try {
            latch.await(1000, TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        latch = CountDownLatch(1)
        doAnswer {
            latch.countDown()
        }.`when`(spyObject).handle(secondCreateProductCommand)
        createProductCommandHandler.handle(secondCreateProductCommand)
        try {
            latch.await(1000, TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }

        verify(spyObject, atLeast(2))

        assertEquals(1, repository.findAll().count())
        assertEquals(2.50, repository.findAll().first().productPrice)
    }
}
