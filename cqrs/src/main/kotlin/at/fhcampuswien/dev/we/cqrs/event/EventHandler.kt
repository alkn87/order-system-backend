package at.fhcampuswien.dev.we.cqrs.event

import java.lang.reflect.ParameterizedType

interface EventHandler<T : Event> {
    fun handle(event: T)

    val eventType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = (this.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<T>
}
