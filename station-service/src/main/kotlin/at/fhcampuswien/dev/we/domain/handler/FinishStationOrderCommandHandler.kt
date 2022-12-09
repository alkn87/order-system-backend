package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.StationOrderRepository
import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.cqrs.event.EventBus
import at.fhcampuswien.dev.we.domain.command.FinishStationOrderCommand
import at.fhcampuswien.dev.we.domain.event.StationOrderFinishedEvent
import at.fhcampuswien.dev.we.domain.model.StationOrderStatus
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class FinishStationOrderCommandHandler(
    private val repository: StationOrderRepository,
    private val eventBus: EventBus
) : CommandHandler<FinishStationOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(FinishStationOrderCommandHandler::class.java)

    override fun handle(command: FinishStationOrderCommand) {
        val finishedStationOrder = repository.findById(command.stationOrderId)
        finishedStationOrder.ifPresentOrElse({
            it.status = StationOrderStatus.FINISHED
            repository.update(it)
            eventBus.dispatchAsync(StationOrderFinishedEvent(it.orderId))
            logger.info("handled FinishStationOrderCommand: $it updated to status ${it.status}")
        }, {
            logger.error("Couldn't handle FinishStationOrderCommand: entity with ID ${command.stationOrderId} not found!")
        })
    }


}
