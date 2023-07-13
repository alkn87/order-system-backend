package at.fhcampuswien.dev.we.controller

import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem
import at.fhcampuswien.dev.we.domain.aggregates.OrderStatus
import at.fhcampuswien.dev.we.domain.aggregates.TestOrder
import at.fhcampuswien.dev.we.repository.OrderRepository
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull


@Controller("/orders")
@ExecuteOn(TaskExecutors.IO)
open class OrderController(private val orderRepository: OrderRepository) {

    private val logger: Logger = LoggerFactory.getLogger(OrderController::class.java)

    @Post
    @Status(CREATED)
    @Consumes(MediaType.APPLICATION_JSON)
    open fun save(@NonNull @NotNull @Valid order: TestOrder) {
        logger.info("order: $order")
        orderRepository.save(
            Order(
                "test22",
                "Table 1",
                listOf(
                    OrderItem("itemId12", "Sandwich", 2.99, 1),
                    OrderItem("itemId34", "Water", 2.99, 1)
                )
            )
        )
    }

    @Get("/total")
    open fun getTotalSales(): HttpResponse<Double> {
        val orders = orderRepository.findByOrderStatus(OrderStatus.FINISHED)
        return HttpResponse.ok(orders.flatMap { it.orderItems }.sumOf { it.unitPrice * it.quantity })
    }

    @Get("/sales")
    open fun getSales(): HttpResponse<Map<String, Int>> {
        val orders = orderRepository.findByOrderStatus(OrderStatus.FINISHED)
        return HttpResponse.ok(orders
            .flatMap { it.orderItems }
            .sortedWith(compareBy({ it.productType }, { it.productName }))
            .groupBy { it.productName }
            .mapValues { (_, items) -> items.sumOf { it.quantity } })
    }

}
