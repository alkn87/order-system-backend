package at.fhcampuswien.dev.we.cqrs.event

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject

@Prototype
class EventBusImpl(
    @Inject private val handlers: List<EventHandler<Event>>,
    @Inject private val asyncHandler: List<AsyncEventHandler<Event>>
) : EventBus {

    private var handlerMap: Map<Class<Event>, EventHandler<Event>> = mutableMapOf()
    private var asyncHandlerMap: Map<Class<Event>, AsyncEventHandler<Event>> = mutableMapOf()

    init {
        handlerMap = handlers.associateBy { it.eventType }
        asyncHandlerMap = asyncHandler.associateBy { it.eventType }
    }


    override fun dispatch(busTypeEvent: Event) {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported event: " + busTypeEvent.javaClass)
        handler.handle(busTypeEvent)
    }

    override fun dispatchAsync(busTypeEvent: Event) {
        val handler = asyncHandlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported event: " + busTypeEvent.javaClass)
        handler.handle(busTypeEvent)
    }
}
