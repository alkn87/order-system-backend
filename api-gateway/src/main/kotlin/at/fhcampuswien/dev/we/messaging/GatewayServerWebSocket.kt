package at.fhcampuswien.dev.we.messaging

import at.fhcampuswien.dev.we.order.model.socket.MessageDTO
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.websocket.WebSocketBroadcaster
import io.micronaut.websocket.annotation.OnClose
import io.micronaut.websocket.annotation.OnMessage
import io.micronaut.websocket.annotation.OnOpen
import io.micronaut.websocket.annotation.ServerWebSocket
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Secured(SecurityRule.IS_ANONYMOUS)
@ServerWebSocket("/ws")
class GatewayServerWebSocket(private val broadcaster: WebSocketBroadcaster) {

    private val logger: Logger = LoggerFactory.getLogger(GatewayServerWebSocket::class.java)

    @OnOpen
    fun onOpen(): Publisher<MessageDTO> {
        logger.info("onOpen")
        return broadcaster.broadcast(MessageDTO("api", "Now making announcements!"))
    }

    @OnClose
    fun onClose(): Publisher<MessageDTO> {
        logger.info("onClose")
        return broadcaster.broadcast(MessageDTO("api", "Closing!"))
    }

    @OnMessage
    fun onMessage(message: String): Publisher<MessageDTO> {
        logger.info("onMessage: $message")
        return broadcaster.broadcast(MessageDTO("api", "Message received! - $message"))
    }

    fun broadcast(command: String) {
        val message = MessageDTO("api", command)
        logger.info("broadcasting: $message")
        broadcaster.broadcastAsync(message)
    }
}
