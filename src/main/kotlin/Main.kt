package org.liviu

import org.liviu.transaction.TransactionJsonWriter
import org.liviu.transaction.TransactionTransformer
import org.liviu.transaction.TransactionWriter

fun main() {
    println("Starting...")

    val writer: TransactionWriter = TransactionJsonWriter()
    val transactionTransformer = TransactionTransformer()
    val csvParser = CsvParser()
    val fileName = "./resources/transactions-demo.csv"

    val transactions = transactionTransformer.fromList(
        csvParser.parse(fileName)
    )
    writer.write(transactions)
}



