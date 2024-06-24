package org.liviu

fun main() {
    println("Starting...")

    var writer: TransactionWriter = TransactionJsonWriter()
    val transactionTransformer = TransactionTransformer()
    val csvParser = CsvParser()
    val fileName = "./resources/transactions-demo.csv"

    val transactions = transactionTransformer.fromList(
        csvParser.parse(fileName)
    )
    writer.write(transactions)
}



