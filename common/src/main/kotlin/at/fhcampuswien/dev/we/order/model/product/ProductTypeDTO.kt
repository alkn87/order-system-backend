package at.fhcampuswien.dev.we.order.model.product

import io.micronaut.core.annotation.Introspected

@Introspected
enum class ProductTypeDTO(val typeName: String) {
    DRINK("DRINK"),
    FOOD("FOOD");

    companion object {
        fun from(typeName: String): ProductTypeDTO {
            return ProductTypeDTO.valueOf(typeName)
        }
    }
}
