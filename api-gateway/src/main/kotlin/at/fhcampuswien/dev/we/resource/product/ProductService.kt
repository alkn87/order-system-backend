package at.fhcampuswien.dev.we.resource.product

import at.fhcampuswien.dev.we.messaging.ProductMessageProducerService
import at.fhcampuswien.dev.we.messaging.ProductRPCService
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import jakarta.inject.Singleton

@Singleton
class ProductService(
    private val messageProducerService: ProductMessageProducerService,
    private val productRPCService: ProductRPCService
) {
    fun createProduct(product: ProductDTO): ProductDTO {
        messageProducerService.send(product)
        return product
    }

    fun blockProduct(product: ProductDTO): ProductDTO {
        messageProducerService.sendBlock(product)
        return product
    }

    fun getAllProducts(): List<ProductDTO> {
        return productRPCService.getAll("NOP".toByteArray())
    }


}
