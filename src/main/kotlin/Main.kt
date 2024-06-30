package org.liviu

import org.liviu.transaction.GoogleSheetsWriter
import org.liviu.transaction.TransactionTransformer
import org.liviu.transaction.TransactionWriter

fun main() {
    println("Starting...")

    val transformer = TransactionTransformer()
    val writer: TransactionWriter = GoogleSheetsWriter()
//    val writer: TransactionWriter = JsonWriter()
    val csvParser = CsvParser()
    val fileName = "./resources/transactions-demo.csv"

    val transactions = transformer.fromList(
        csvParser.parse(fileName)
    )
    val aggregated = transformer.aggregateToCategories(transactions)
    writer.write(aggregated)
}



