package at.fhcampuswien.sde.ordersystem.broker

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessagingHandler {

    @Bean
    fun registerQueue(): Queue {
        return Queue("myQ", false)
    }

    @RabbitListener(queues = ["myQ"])
    private fun bindQueue(message: String) {
        println("Received $message")
    }
}
