package at.fhcampuswien.dev.we.cqrs.event


interface AsyncEventHandler<T : Event> {
    fun handle(event: T)

    val eventType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = this.javaClass.methods[0].parameterTypes[0] as Class<T>
}
