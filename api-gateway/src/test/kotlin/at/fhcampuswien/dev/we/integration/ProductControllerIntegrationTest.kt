package at.fhcampuswien.dev.we.integration


import at.fhcampuswien.dev.we.messaging.ProductMessageProducerService
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.json.tree.JsonObject
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

@MicronautTest(transactional = false)
class ProductControllerIntegrationTest {

    @Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Inject
    lateinit var productMessageProducerService: ProductMessageProducerService

    @Test
    fun shouldSendCreateProductCommandWithValidRequest() {
        val productCreationRequest = ProductDTO(
            "Coke",
            3.20,
            "DRINK"
        )

        val response = httpClient.toBlocking().exchange(
            HttpRequest.POST("/product/create", productCreationRequest),
            ProductDTO::class.java)
        assertEquals(HttpStatus.CREATED.code, response.status.code)
        verify(productMessageProducerService).send(productCreationRequest)
    }

    @Test
    fun shouldReturnBadRequestWithInvalidRequest() {
        val invalidRequest = JsonObject.createObjectNode(mapOf("productName" to JsonObject.createStringNode("Coke")))

        val responseException = assertThrows<HttpClientResponseException> { httpClient.toBlocking().exchange(
            HttpRequest.POST("/product/create", invalidRequest.toString()),
            ProductDTO::class.java) }
        assertEquals(HttpStatus.BAD_REQUEST.code, responseException.status.code)
        verifyNoInteractions(productMessageProducerService)
    }

    @MockBean(ProductMessageProducerService::class)
    fun productMessageProducerService(): ProductMessageProducerService {
        return mock(ProductMessageProducerService::class.java)
    }
}
