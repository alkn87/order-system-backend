package at.fhcampuswien.dev.we
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


@MicronautTest
class ApiGatewayTest {

    @Test
    fun testItWorks(application: EmbeddedApplication<*>) {
        Assertions.assertTrue(application.isRunning)
    }

}
