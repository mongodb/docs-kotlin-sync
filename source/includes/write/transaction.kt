import com.mongodb.*
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document

fun main() {
// start-transaction
    // Creates a new MongoClient with a connection string
    val client = MongoClients.create("<connection string>")

    // Gets the database and collection
    val restaurantsDb = client.getDatabase("sample_restaurants")
    val restaurantsCollection: MongoCollection<Document> = restaurantsDb.getCollection("restaurants")
    
    fun insertDocuments(session: ClientSession) {
        // Sets options for the collection operation within the transaction
        val restaurantsCollectionWithOptions = restaurantsCollection
            .withWriteConcern(WriteConcern("majority"))
            .withReadConcern(ReadConcern.LOCAL)

        // Inserts documents within the transaction
        restaurantsCollectionWithOptions.insertOne(
            session,
            Document("name", "Kotlin Sync Pizza").append("cuisine", "Pizza")
        )
        restaurantsCollectionWithOptions.insertOne(
            session,
            Document("name", "Kotlin Sync Burger").append("cuisine", "Burger")
        )
    }

    // Starts a client session
    client.startSession().use { session ->
        try {
            val txnOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build()

            // Use the withTransaction method to start a transaction and execute the lambda function
            session.withTransaction({
                insertDocuments(session)
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