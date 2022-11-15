package at.fhcampuswien.dev.we.cqrs.event

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject
import java.util.function.Function
import java.util.stream.Collectors.toMap

@Prototype
class EventBusImpl(@Inject private val handlers: List<EventHandler<Event>>) : EventBus {

    private var handlerMap: Map<Class<Event>, EventHandler<Event>> = mutableMapOf()

    init {
        handlerMap = handlers.stream()
            .collect(
                toMap(
                    EventHandler<Event>::eventType,
                    Function.identity()
                )
            )
    }


    override fun dispatch(busTypeEvent: Event) {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported event: " + busTypeEvent.javaClass)
        handler.handle(busTypeEvent)
    }
}
