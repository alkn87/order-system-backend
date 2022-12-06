package at.fhcampuswien.dev.we.cqrs.command

import java.lang.reflect.ParameterizedType

interface CommandHandler<T : Command> {
    fun handle(command: T)

    val commandType: Class<T>
        @Suppress("UNCHECKED_CAST")
        get() = (this.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<T>

}
