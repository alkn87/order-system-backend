package at.fhcampuswien.dev.we.cqrs.query


interface QueryHandler<T : Query, R> {
    fun handle(query: T): R

    val queryType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = this.javaClass.methods[0].parameterTypes[0] as Class<T>
}
