package at.fhcampuswien.dev.we

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/product")
class OrderController(private val orderService: OrderService) {

    @Get("/create")
    fun createOrder(): Order = orderService.createOrder()
}
