package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/order")
class OrderController(private val orderService: OrderService) {

    @Post("/create")
    fun createOrder(@Valid order: OrderDTO): HttpResponse<OrderDTO> {
        val orderResponse = orderService.createOrder()
        return HttpResponse.created(orderResponse)
    }
}
