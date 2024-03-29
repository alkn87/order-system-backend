package at.fhcampuswien.dev.we.integration


import at.fhcampuswien.dev.we.messaging.ProductMessageProducerService
import at.fhcampuswien.dev.we.messaging.ProductRPCService
import at.fhcampuswien.dev.we.order.model.integration.command.ProductCommandType
import at.fhcampuswien.dev.we.order.model.integration.command.ProductIntegrationCommand
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.core.type.Argument
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

    @Inject
    lateinit var productRPCService: ProductRPCService

    @MockBean(ProductMessageProducerService::class)
    fun productMessageProducerService(): ProductMessageProducerService {
        return mock(ProductMessageProducerService::class.java)
    }

    @MockBean(ProductRPCService::class)
    fun productProductRPCService(): ProductRPCService {
        return mock(ProductRPCService::class.java)
    }

    @Test
    fun shouldSendCreateProductCommandWithValidRequest() {
        val productCreationRequest = ProductDTO(
            productName = "Coke",
            productPrice = 3.20,
            productType = "DRINK",
            productStatus = "AVAILABLE",
            id = null
        )

        val productIntegrationCommand = ProductIntegrationCommand(
            productCreationRequest,
            ProductCommandType.CREATE
        )

        val response = httpClient.toBlocking().exchange(
            HttpRequest.POST("/product/create", productCreationRequest),
            ProductDTO::class.java)
        assertEquals(HttpStatus.CREATED.code, response.status.code)
        verify(productMessageProducerService).send(productIntegrationCommand)
    }

    @Test
    fun shouldReturnBadRequestWithInvalidRequest() {
        val invalidRequest = JsonObject.createObjectNode(mapOf("productName" to JsonObject.createStringNode("Coke")))

        val responseException = assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(
                HttpRequest.POST("/product/create", invalidRequest.toString()),
                ProductDTO::class.java
            )
        }
        assertEquals(HttpStatus.BAD_REQUEST.code, responseException.status.code)
        verifyNoInteractions(productMessageProducerService)
    }

    @Test
    fun shouldReturnProductsWithGetRequest() {
        val response = httpClient.toBlocking().exchange(
                HttpRequest.GET<List<ProductDTO>>("/product"),
                Argument.listOf(ProductDTO::class.java)
            )
        assertEquals(HttpStatus.OK.code, response.status.code)
        verify(productRPCService).getAll("NOP".toByteArray())
    }
}
