package at.fhcampuswien.dev.we.controller

import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem
import at.fhcampuswien.dev.we.domain.aggregates.TestOrder
import at.fhcampuswien.dev.we.repository.OrderRepository
import io.micronaut.core.annotation.NonNull
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
//    private val orderRepository: OrderRepository

//    @Get
//    fun list(): List<Order> = orderRepository.list()

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

}
