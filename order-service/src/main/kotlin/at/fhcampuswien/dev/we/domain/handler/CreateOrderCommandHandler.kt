package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateOrderCommandHandler : CommandHandler<CreateOrderCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateOrderCommandHandler::class.java)

    @Async
    override fun handle(command: CreateOrderCommand) {
        logger.info("handled CreateOrderCommand: ${command.deliverTo}")
    }
}
