import com.mongodb.kotlin.client.MongoClient
import org.bson.Document

fun main() {
    //start-execute
    val explanation = database.runCommand({
            explain: {
                find: 'restaurants'
            }
        })
    //end-execute

    //start-read-preference
    val command = { "hello": 1 }
    val commandReadPreference = { readPreference: "secondary" }

    val commandResult = database.runCommand(command, readPreference)
    //end-read-preference
}