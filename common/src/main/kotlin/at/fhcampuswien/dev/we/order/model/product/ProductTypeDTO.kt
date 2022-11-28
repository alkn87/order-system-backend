package at.fhcampuswien.dev.we.order.model.product

enum class ProductTypeDTO(val typeName: String) {
    DRINK("DRINK"),
    FOOD("FOOD");

    companion object {
        fun from(typeName: String): ProductTypeDTO {
            return ProductTypeDTO.valueOf(typeName)
        }
    }
}
