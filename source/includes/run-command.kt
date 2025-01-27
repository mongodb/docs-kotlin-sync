import com.mongodb.kotlin.client.MongoClient
import org.bson.Document

fun main() {
    //start-execute
    val commandToExplain = Document( "find", "restaurants")
    val explanation = database.runCommand(Document("explain", commandToExplain))
    //end-execute

    //start-read-preference
    val command = Document("hello", 1)
    val commandReadPreference = Document( "readPreference", "secondary" )

    val commandResult = database.runCommand(command, commandReadPreference)
    //end-read-preference

    //start-build-info
    println( database.runCommand( Document( "buildInfo", 1 ) );
    //end-build-info
}