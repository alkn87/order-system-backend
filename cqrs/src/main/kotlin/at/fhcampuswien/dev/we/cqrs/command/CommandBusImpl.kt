package at.fhcampuswien.dev.we.cqrs.command

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject


@Prototype
class CommandBusImpl(@Inject private val handlers: List<CommandHandler<Command>>) : CommandBus {

    private var handlerMap: Map<Class<Command>, CommandHandler<Command>> = mutableMapOf()

    init {
        handlerMap = handlers.associateBy { it.commandType }
    }


    override fun dispatch(busTypeEvent: Command) {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported command: " + busTypeEvent.javaClass)
        handler.handle(busTypeEvent)
    }

    override fun dispatchAsync(busTypeEvent: Command): Any? {
        TODO("Not yet implemented")
    }
}
