import com.mongodb.*
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClients
import org.bson.Document

// start-data-class
data class Restaurant(val name: String, val cuisine: String)
// end-data-class

fun main() {
// start-transaction
    // Creates a new MongoClient with a connection string
    val client = MongoClients.create("<connection string>")

    // Gets the database and collection
    val database = client.getDatabase("sample_restaurants")
    val collection = database.getCollection<Restaurant>("restaurants")
    
    // Defines options and inserts restaurants into the collection
    fun insertRestaurantsInTransaction(session: ClientSession) {
        // Sets options for the collection operation within the transaction
        val restaurantsCollectionWithOptions = collection
            .withWriteConcern(WriteConcern("majority"))
            .withReadConcern(ReadConcern.LOCAL)

        // Inserts restaurants within the transaction
        restaurantsCollectionWithOptions.insertOne(
            session,
            Restaurant("name", "Kotlin Sync Pizza").append("cuisine", "Pizza")
        )
        restaurantsCollectionWithOptions.insertOne(
            session,
            Restaurant("name", "Kotlin Sync Burger").append("cuisine", "Burger")
        )
    }

    // Starts a client session
    client.startSession().use { session ->
        try {
            val txnOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build()

            // Uses the withTransaction method to start a transaction and run the given function
            session.withTransaction({
                insertRestaurantsInTransaction(session)
                println("Transaction succeeded")
            }, txnOptions)
        } catch (e: Exception) {
            println("Transaction failed: ${e.message}")
        }
    }

    // Closes the MongoClient
    client.close()
// end-transaction
}