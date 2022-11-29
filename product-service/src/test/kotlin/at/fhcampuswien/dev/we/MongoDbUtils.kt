package at.fhcampuswien.dev.we

import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

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
