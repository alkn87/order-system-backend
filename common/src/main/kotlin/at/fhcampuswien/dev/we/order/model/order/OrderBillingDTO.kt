package at.fhcampuswien.dev.we.order.model.order

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class OrderBillingDTO(
    @NotBlank
    var deliverTo: String,
    @NotBlank
    var orderItems: List<OrderItemDTO>
)
