package org.example
import com.mongodb.ConnectionString
import com.mongodb.CursorType
import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.MongoClient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

// start-data-class
data class Restaurant(
    @BsonId
    val id: ObjectId,
    val name: String
)
// end-data-class

fun main() {
    val uri = "<connection string URI>"

    val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .retryWrites(true)
        .build()

    val mongoClient = MongoClient.create(settings)
    val database = mongoClient.getDatabase("sample_restaurants")
    val collection = database.getCollection<Restaurant>("restaurants")

    // start-cursor-iterate
   val results = collection.find()

   results.forEach { result ->
       println(results)
   }
    // end-cursor-iterate

    // start-cursor-next
   val results = collection.find<Restaurant>(eq(Restaurant::name.name, "Dunkin' Donuts"))

   println(results.cursor().next())
    // end-cursor-next

    // start-cursor-list
    val results = collection.find<Restaurant>(eq(Restaurant::name.name, "Dunkin' Donuts"))
    val resultsList = results.toList()

    for (result in resultsList) {
        println(result)
    }
    // end-cursor-list

    // start-cursor-close
    val results = collection.find<Restaurant>()

    // Handle your results here

    results.cursor().close()
    // end-cursor-close

    // start-tailable-cursor
    val results = collection.find<Restaurant>().cursorType(CursorType.TailableAwait)
    // end-tailable-cursor
}

