package at.fhcampuswien.dev.we

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.EmbeddedApplication
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class DatabaseBaseTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>
    private val map = mutableMapOf(
        "mongodb.uri" to MongoDbUtils.mongoDbUri,
        "mongodb.package-names" to listOf("at.fhcampuswien.dev.we")
    )
    val context: ApplicationContext = ApplicationContext.run(map)

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupDatabase(): Unit {
            MongoDbUtils.startMongoDb()

        }

        @JvmStatic
        @AfterAll
        fun closeDatabase(): Unit {
            MongoDbUtils.closeMongoDb()

        }
    }

}
