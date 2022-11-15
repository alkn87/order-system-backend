package at.fhcampuswien.dev.we.cqrs.command

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject
import java.util.function.Function
import java.util.stream.Collectors.toMap

@Prototype
class CommandBusImpl(@Inject private val handlers: List<CommandHandler<Command>>) : CommandBus {

    private var handlerMap: Map<Class<Command>, CommandHandler<Command>> = mutableMapOf()

    init {
        handlerMap = handlers.stream()
            .collect(
                toMap(
                    CommandHandler<Command>::commandType,
                    Function.identity()
                )
            )
    }


    override fun dispatch(busTypeEvent: Command) {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported command: " + busTypeEvent.javaClass)
        handler.handle(busTypeEvent)
    }
}
