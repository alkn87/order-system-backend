package at.fhcampuswien.sde.ordersystem

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class OrdersystemApplication {
	@Bean
	fun runner(template: RabbitTemplate): ApplicationRunner {
		return ApplicationRunner { args: ApplicationArguments? -> template.convertAndSend("myQ", "Hello, world!") }
	}
}

fun main(args: Array<String>) {
	runApplication<OrdersystemApplication>(*args)
}


