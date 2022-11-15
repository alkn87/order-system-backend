package at.fhcampuswien.dev.we.database

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("db")
interface MongoDbConfiguration : Named {

    val collection: String
}
