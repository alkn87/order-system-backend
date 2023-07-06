package at.fhcampuswien.dev.we.resource.event

import at.fhcampuswien.dev.we.data.UpdateEvent
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/updates")
class UpdateEventController(private val eventPublisher: ApplicationEventPublisher<UpdateEvent>) : ApplicationEventListener<UpdateEvent> {

    private val logger: Logger = LoggerFactory.getLogger(UpdateEventController::class.java)

    private val coroutineJob: Job = Job()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + coroutineJob)

    private val messageChannel: Channel<UpdateEvent> = Channel()
    private val messageSharedFlow: SharedFlow<UpdateEvent> =
        messageChannel.consumeAsFlow().shareIn(coroutineScope, SharingStarted.Eagerly)
//    @OptIn(ObsoleteCoroutinesApi::class)
//    private val tickerChannel: ReceiveChannel<Unit> = ticker(5*1000, 0)
//    @OptIn(ObsoleteCoroutinesApi::class)
//    private val tickerSharedFlow: SharedFlow<Unit> =
//        tickerChannel.consumeAsFlow().shareIn(coroutineScope, SharingStarted.Eagerly)

    private val tickerFlow: SharedFlow<Unit> = flow {
        while (true) {
            emit(Unit)
            val timestamp = java.time.LocalTime.now().toString()
            eventPublisher.publishEvent(UpdateEvent(timestamp))
            delay(60*1000)
        }
    }.shareIn(coroutineScope, SharingStarted.Eagerly)


    @Get(produces = [MediaType.TEXT_EVENT_STREAM])
    fun stream(): Flow<UpdateEvent> {
        return messageSharedFlow
            .onSubscription {
                logger.info("RECEIVED SUBSCRIPTION")
            }
            .onEach { logger.info(it.data) }
            .onCompletion {
                logger.info("CANCELLED")
            }
    }

    override fun onApplicationEvent(event: UpdateEvent) {
        runBlocking {
            launch(coroutineScope.coroutineContext) {
                logger.info("onApplicationEvent: ${event.data}")
                messageChannel.send(UpdateEvent(event.data))
            }
        }
    }

    @Post("/send")
    fun trigger(): HttpResponse<String> {
        // get current timestamp:
        val timestamp = java.time.LocalTime.now().toString()
        eventPublisher.publishEvent(UpdateEvent(timestamp))
        logger.debug("triggered")
        return HttpResponse.ok("triggered")
    }

}
