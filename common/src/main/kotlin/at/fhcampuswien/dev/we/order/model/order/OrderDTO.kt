package at.fhcampuswien.dev.we.order.model.order

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class OrderDTO(
    @NotBlank
    var orderAgent: String,
    @NotBlank
    var deliverTo: String,
    @NotBlank
    var orderItems: List<OrderItemDTO>,
    var orderStatus: String?,
    var id: String?
)
