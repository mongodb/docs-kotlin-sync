import com.mongodb.kotlin.client.MongoClient
import com.mongodb.MongoClientSettings
import com.mongodb.ConnectionString
import com.mongodb.management.JMXConnectionPoolListener

fun main() {
    val uri = "<connection string uri>"

// Instantiate your JMX listener
    val connectionPoolListener = JMXConnectionPoolListener()

// Include the listener in your client settings
    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .applyToConnectionPoolSettings {
            it.addConnectionPoolListener(connectionPoolListener)
        }
        .build()
    val mongoClient: MongoClient = MongoClient.create(settings)

// Pause execution
    try {
        println("Navigate to JConsole to see your connection pools...")
        Thread.sleep(Long.MAX_VALUE)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    mongoClient.close()
}