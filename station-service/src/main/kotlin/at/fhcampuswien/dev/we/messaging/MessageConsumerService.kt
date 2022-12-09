package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.cqrs.query.QueryBus
import at.fhcampuswien.dev.we.domain.command.CreateStationOrderCommand
import at.fhcampuswien.dev.we.domain.command.FinishStationOrderCommand
import at.fhcampuswien.dev.we.domain.query.GetOrdersByStationQuery
import at.fhcampuswien.dev.we.order.model.integration.event.OrderCreatedIntegrationEvent
import at.fhcampuswien.dev.we.order.model.station.StationOrderDto
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus, private val queryBus: QueryBus) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("station-events")
    fun onReceived(event: OrderCreatedIntegrationEvent) {
        logger.info("station-service - event received: $event")
        this.commandBus.dispatch(CreateStationOrderCommand(event.orderId, event.orderItems, event.deliverTo))
    }

    @Queue("station-queries")
    fun onQueryRpc(stationType: String): List<StationOrderDto> {
        logger.info("product-queries - rpc call received")
        @Suppress("UNCHECKED_CAST")
        return queryBus.dispatch(GetOrdersByStationQuery(stationType)) as List<StationOrderDto>
    }

    @Queue("station-commands-finish")
    fun onStationCommandFinished(stationOrderId: String) {
        commandBus.dispatch(FinishStationOrderCommand(stationOrderId))
    }
}
