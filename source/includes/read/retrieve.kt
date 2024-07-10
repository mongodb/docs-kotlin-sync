import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.*
import com.mongodb.client.model.Filters.*

data class Restaurant(val cuisine: String)

fun main() {
    val uri = "<connection string URI>"
    
    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .retryWrites(true)
        .build()

    val mongoClient = MongoClient.create(settings)
    val database = mongoClient.getDatabase("sample_restaurants")
    val collection = database.getCollection<Restaurant>("restaurants")

    // start-find
    val results = collection.find(eq("cuisine", "Spanish"))
    // end-find

    // start-find-iterate
    val resultsToPrint = collection.find(eq("cuisine", "Spanish"))
    resultsToPrint.forEach { result ->
        println(result)
    }
    // end-find-iterate

    // start-find-all
    val allRestaurants = collection.find()
    // end-find-all

    // start-modified-find
    val modifiedResults = collection.find(eq("cuisine", "Spanish")).limit(10).maxTime(10000)
    // end-modified-find
}
