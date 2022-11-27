package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.command.CommandHandler
import at.fhcampuswien.dev.we.domain.command.CreateProductCommand
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CreateProductCommandHandler : CommandHandler<CreateProductCommand> {

    private val logger: Logger = LoggerFactory.getLogger(CreateProductCommandHandler::class.java)

    @Async
    override fun handle(command: CreateProductCommand) {
        logger.info("handled CreateProductCommand: $command")
    }
}
