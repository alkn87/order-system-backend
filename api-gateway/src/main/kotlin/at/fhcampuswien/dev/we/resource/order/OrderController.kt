package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import javax.validation.Valid

@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
@Controller("/order")
class OrderController(private val orderService: OrderService) {

    @Secured("service", "admin", "manager")
    @Post("/create")
    fun createOrder(@Valid order: OrderDTO): HttpResponse<OrderDTO> {
        val orderResponse = orderService.createOrder(order)
        return HttpResponse.created(orderResponse)
    }
}
