package at.fhcampuswien.dev.we.cqrs.query

import java.lang.reflect.ParameterizedType

interface QueryHandler<T : Query, R> {
    fun handle(query: T): R

    val queryType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = (this.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<T>
}
