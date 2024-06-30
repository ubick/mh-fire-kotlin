package org.liviu.transaction

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonWriter: TransactionWriter {
    private val format = Json { prettyPrint = true }

    override fun write(transactions: List<Transaction>) {
        println(format.encodeToString(transactions))
    }

    override fun write(data: Map<String, Double>) {
        println(format.encodeToString(data))
    }
}