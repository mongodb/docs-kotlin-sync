// start-find
val filter = <filter>
val results = collection.find(filter)
results.forEach { result ->
    print(result)
}
// end-find

// start-count-all
val count = collection.countDocuments()
print(count)
// end-count-all

// start-count-query
val filter = <filter>
val count = collection.countDocuments(filter)
print(count)
// end-count-query

// start-estimated-count
val count = collection.estimatedDocumentCount()
print(count)
// end-estimated-count

// start-distinct
val distinctResults = collection.distinct("<field name>")
distinctResults.forEach { result ->
    print(result)
}
// end-distinct

// start-watch
val changeStream = collection.watch()
changeStream.forEach { changeEvent ->
    print(changeEvent)
}
// end-watch
