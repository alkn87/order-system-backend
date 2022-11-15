package at.fhcampuswien.dev.we.cqrs.query

import io.micronaut.context.annotation.Prototype
import jakarta.inject.Inject
import java.util.function.Function
import java.util.stream.Collectors.toMap

@Prototype
class QueryBusImpl(@Inject private val handlers: List<QueryHandler<Query, *>>) : QueryBus {

    private var handlerMap: Map<Class<Query>, QueryHandler<Query, *>> = mutableMapOf()

    init {
        handlerMap = handlers.stream()
            .collect(
                toMap(
                    QueryHandler<Query,*>::queryType,
                    Function.identity()
                )
            )
    }


    override fun dispatch(busTypeEvent: Query): Any? {
        val handler = handlerMap[busTypeEvent.javaClass]
            ?: throw UnsupportedOperationException("Unsupported query: " + busTypeEvent.javaClass)
        return handler.handle(busTypeEvent)
    }
}
