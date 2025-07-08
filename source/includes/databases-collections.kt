import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ReadConcern
import com.mongodb.ReadPreference
import com.mongodb.WriteConcern
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

fun main() {
    val uri = "<connection string URI>"
    
    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .retryWrites(true)
        .build()

    val mongoClient = MongoClient.create(settings)
    val database = mongoClient.getDatabase("sample_restaurants")
    val collection = database.getCollection<Restaurant>("restaurants")

    // Accesses the "test_database" database
    // start-access-database
    val db: MongoDatabase = client.getDatabase("test_database")
    // end-access-database

    // Accesses the "test_collection" collection
    // start-access-collection
    val collection: MongoCollection<Document> = client.getDatabase("test_database").getCollection("test_collection")
    // end-access-collection

    // Explicitly creates the "example_collection" collection
    // start-create-collection
    client.getDatabase("test_database").createCollection("example_collection")
    // end-create-collection

    // Lists the collections in the "test_database" database
    // start-find-collections
    for (collectionInfo in client.getDatabase("test_database").listCollections()) {
        println(collectionInfo.toJson())
    }
    // end-find-collections


    // Deletes the "test_collection" collection
    // start-drop-collection
    client.getDatabase("test_database").getCollection("test_collection").drop()
    // end-drop-collection
}
