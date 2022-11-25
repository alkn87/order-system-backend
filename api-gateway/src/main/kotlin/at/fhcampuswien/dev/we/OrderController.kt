package at.fhcampuswien.dev.we

import at.fhcampuswien.dev.we.order.OrderDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/product")
class OrderController(private val orderService: OrderService) {

    @Get("/create")
    fun createOrder(): HttpResponse<OrderDTO> {
        val order = orderService.createOrder()
        return HttpResponse.created(order)
    }
}
