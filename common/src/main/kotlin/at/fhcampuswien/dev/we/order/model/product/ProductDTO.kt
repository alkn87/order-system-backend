package at.fhcampuswien.dev.we.order.model.product

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class ProductDTO(
    @NotBlank
    val productName: String,
    @NotBlank
    val productPrice: Double,
    @NotBlank
    val productType: String,
    val productStatus: String? = "",
    val id: String? = ""
)
