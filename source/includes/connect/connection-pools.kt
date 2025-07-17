import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.MongoClient

fun main() {
    // start-uri-option
    val uri = "mongodb://<host>:<port>/?maxPoolSize=50"
    val client = MongoClient.create(uri)
    // end-uri-option

    // start-client-settings
    val mongoClient = MongoClient.create(
        MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("<connection string>"))
            .applyToConnectionPoolSettings { builder -> 
                builder.maxSize(50) 
            }
            .build()
    )
    // end-client-settings
}