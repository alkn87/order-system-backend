package at.fhcampuswien.dev.we

import at.fhcampuswien.dev.we.domain.aggregates.Order
import at.fhcampuswien.dev.we.domain.aggregates.OrderItem
import at.fhcampuswien.dev.we.repository.OrderRepository
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@MicronautTest(transactional=false)
@Testcontainers
class OrderServiceTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>
    private val map = mutableMapOf<String, Any>("mongodb.uri" to MongoDbUtils.mongoDbUri)
    private val context: ApplicationContext = ApplicationContext.run(map)
    private val repository: OrderRepository = context.getBean(OrderRepository::class.java)

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
        repository.save(
            Order(
                "test22",
                "Table 1",
                listOf(
                    OrderItem("itemId12", "Sandwich", 2.99, 1),
                    OrderItem("itemId34", "Water", 2.99, 1)
                )
            )
        )
        assertEquals(1, repository.findAll().count())
        assertEquals("test22", repository.findAll().first().orderAgent)
    }

    @AfterEach
    fun cleanDatabase() {
        repository.deleteAll()
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupDatabase() {
            MongoDbUtils.startMongoDb()

        }

        @JvmStatic
        @AfterAll
        fun closeDatabase() {
            MongoDbUtils.closeMongoDb()

        }
    }

    object MongoDbUtils {

        private var mongoDBContainer: MongoDBContainer =
            MongoDBContainer(DockerImageName.parse("mongo:5"))
                .withExposedPorts(27017)

        fun startMongoDb() {
            if (!mongoDBContainer.isRunning) {
                mongoDBContainer.start()
            }
        }

        fun closeMongoDb() = mongoDBContainer.close()

        val mongoDbUri: String
            get() {
                if (!mongoDBContainer.isRunning) {
                    startMongoDb()
                }
                return mongoDBContainer.replicaSetUrl
            }
    }

}
