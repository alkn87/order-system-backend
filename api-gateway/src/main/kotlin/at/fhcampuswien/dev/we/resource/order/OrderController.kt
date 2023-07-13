package at.fhcampuswien.dev.we.resource.order

import at.fhcampuswien.dev.we.order.model.order.OrderBillingDTO
import at.fhcampuswien.dev.we.order.model.order.OrderDTO
import at.fhcampuswien.dev.we.order.model.station.StationOrderDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import java.util.*
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

    @Secured("service", "admin", "manager")
    @Post("/bill")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    fun billOrder(deliverTo: String): HttpResponse<String> {
        val orderIdResponse = orderService.billOrder(deliverTo)
        return HttpResponse.created(orderIdResponse)
    }

    @Secured("service", "admin", "manager")
    @Get("/station/{stationType}")
    fun getOrdersByStation(stationType: String): HttpResponse<List<StationOrderDto>> {
        val ordersByStationResponse = orderService.getOrdersByStation(stationType)
        if (ordersByStationResponse.isEmpty()) {
            return HttpResponse.noContent()
        }
        return HttpResponse.ok(ordersByStationResponse)
    }

    @Secured("service", "admin", "manager")
    @Get
    fun getOrders(): HttpResponse<List<OrderDTO>> {
        val orderResponse = orderService.getOrders()
        if (orderResponse.isEmpty()) {
            return HttpResponse.noContent()
        }
        return HttpResponse.ok(orderResponse)
    }

    @Secured("service", "admin", "manager")
    @Get("/billing")
    fun getOrdersForBilling(): HttpResponse<List<OrderBillingDTO>> {
        val orderResponse = orderService.getOrdersForBilling()
        if (orderResponse.isEmpty()) {
            return HttpResponse.noContent()
        }
        return HttpResponse.ok(orderResponse)
    }

    @Secured("service", "admin", "manager")
    @Post("/station/finish")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    fun finishStationOrder(stationOrderId: String): HttpResponse<String> {
        orderService.finishStationOrder(stationOrderId)
        return HttpResponse.created(stationOrderId)
    }
}
