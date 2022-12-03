package at.fhcampuswien.dev.we.cqrs.query

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject

@Prototype
class QueryBusImpl(@Inject private val handlers: List<QueryHandler<Query, *>>) : QueryBus {

    private var handlerMap: Map<Class<Query>, QueryHandler<Query, *>> = mutableMapOf()

    init {
        handlerMap = handlers.associateBy { it.queryType }
    }


    override fun dispatch(busTypeEvent: Query): Any? {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported query: " + busTypeEvent.javaClass)
        return handler.handle(busTypeEvent)
    }
}
