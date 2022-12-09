package at.fhcampuswien.dev.we.messaging

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.rabbitmq.annotation.Queue
import io.micronaut.rabbitmq.annotation.RabbitListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Requires(notEnv = [Environment.TEST])
@RabbitListener
class MessageConsumerService(private val webSocket: GatewayServerWebSocket) {

    private val logger: Logger = LoggerFactory.getLogger(MessageConsumerService::class.java)

    @Queue("gateway-commands")
    fun update(command: String) {
        logger.info("data: $command")
        webSocket.broadcast(command)
    }
}
