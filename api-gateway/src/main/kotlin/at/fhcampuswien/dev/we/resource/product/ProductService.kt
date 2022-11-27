package at.fhcampuswien.dev.we.resource.product

import at.fhcampuswien.dev.we.messaging.ProductMessageProducerService
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import jakarta.inject.Singleton

@Singleton
class ProductService(private val messageProducerService: ProductMessageProducerService) {
    fun createProduct(product: ProductDTO): ProductDTO {
        messageProducerService.send(product)
        return product
    }


}
