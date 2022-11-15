package at.fhcampuswien.dev.we.cqrs.command

interface CommandHandler<T : Command> {
    fun handle(command: T)

    val commandType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = this.javaClass.methods[0].parameterTypes[0] as Class<T>
}
