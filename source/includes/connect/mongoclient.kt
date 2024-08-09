import com.mongodb.*
import com.mongodb.kotlin.client.MongoClient
import org.bson.BsonInt64
import org.bson.Document

fun main() {


    // start-connect-to-atlas
    // Replace the placeholder with your Atlas connection string
    val uri = "<connection string>"

    // Construct a ServerApi instance using the ServerApi.builder() method
    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()
    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .serverApi(serverApi)
        .build()

    // Create a new client and connect to the server
    val mongoClient = MongoClient.create(settings)
    val database = mongoClient.getDatabase("sample_mflix")

    try {
        // Send a ping to confirm a successful connection
        val command = Document("ping", BsonInt64(1))
        val commandResult = database.runCommand(command)
        println("Pinged your deployment. You successfully connected to MongoDB!")
    } catch (me: MongoException) {
        System.err.println(me)
    }
    // end-connect-to-atlas

    // Replication set connection options

    // Using ConnectionString class
    // start-replica-set-connection-string
    val connectionString = ConnectionString("mongodb://host1:27017,host2:27017,host3:27017/")
    val mongoClient = MongoClient.create(connectionString)
    // end-replica-set-connection-string

    //  Using MongoClientSettings class 
    // start-replica-set-client-settings
    val seed1 = ServerAddress("host1", 27017)
    val seed2 = ServerAddress("host2", 27017)
    val seed3 = ServerAddress("host3", 27017)
    val settings = MongoClientSettings.builder()
        .applyToClusterSettings { builder ->
            builder.hosts(
                listOf(seed1, seed2, seed3)
            )
        }
        .build()
    val mongoClient = MongoClient.create(settings)
    // end-replica-set-client-settings
}
