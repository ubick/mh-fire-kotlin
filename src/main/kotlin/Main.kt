package org.liviu

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main() {
    println("Starting...")

    val transactionTransformer = TransactionTransformer()
    val csvParser = CsvParser()
    val fileName = "./resources/transactions-demo.csv"

    val transactions = transactionTransformer.fromList(
        csvParser.parse(fileName)
    )

    val format = Json { prettyPrint = true }
    println(format.encodeToString(transactions))
}



