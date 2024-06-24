package org.liviu.transaction

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TransactionJsonWriter : TransactionWriter {
    override fun write(transactions: List<Transaction>) {
        val format = Json { prettyPrint = true }
        println(format.encodeToString(transactions))
    }
}