package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.cqrs.command.CommandBus
import at.fhcampuswien.dev.we.domain.command.CreateOrderCommand
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val commandBus: CommandBus) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("order-commands")
    fun onReceived(data: ByteArray) {
        logger.info("order-service - data received: $data")
        commandBus.dispatch(CreateOrderCommand("Table 1"))
    }
}
