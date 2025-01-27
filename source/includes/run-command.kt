import com.mongodb.kotlin.client.MongoClient
import org.bson.Document

fun main() {
    //start-execute
    val command = {
        find: "restaurants",
        filter: { cuisine: "Italian" },
        projection: { name: 1, rating: 1, address: 1 },
        sort: { name: 1 },
        limit: 5
      }
    val commandResult = database.runCommand(command)
    //end-execute

    //start-read-preference
    val command = { "hello": 1 }
    val commandReadPreference = { readPreference: "secondary" }

    val commandResult = database.runCommand(command, readPreference)
    //end-read-preference
}