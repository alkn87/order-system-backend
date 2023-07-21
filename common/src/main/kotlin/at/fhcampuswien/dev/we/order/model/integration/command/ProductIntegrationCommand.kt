package at.fhcampuswien.dev.we.order.model.integration.command

import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.core.annotation.Introspected

@Introspected
data class ProductIntegrationCommand(
    val productDTO: ProductDTO,
    val commandType: ProductCommandType
)
