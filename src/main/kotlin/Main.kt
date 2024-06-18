package org.liviu

import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileReader

fun main() {
    println("Starting...")

    val transactionParser = TransactionParser()

    val fileName = "./resources/transactions-demo.csv"
    val data = parseCsv(fileName)

    val transactions = transactionParser.parse(
        convertTransactions(data)
    )

    val format = Json { prettyPrint = true }
    println(format.encodeToString(transactions))
}

fun parseCsv(fileName: String): MutableList<Map<String, String>>  {
    val data = mutableListOf<Map<String, String>>()
    BufferedReader(FileReader(fileName)).use { br ->
        val headers = br.readLine().split(",")
        br.lines().forEach { line ->
            val fields = line.split(",").map { it.replace("\"", "") }
            val row = headers.zip(fields).associate { it.first to it.second.trim() }
            data.add(row)
        }
    }
    return data
}

fun convertTransactions(rawData: MutableList<Map<String, String>>): MutableList<Transaction> {
    val transactions: MutableList<Transaction> = mutableListOf()

    for (item in rawData) {
        val t = Transaction(
            LocalDate.parse(item["DATE"]?:""),
            item["AMOUNT"]?.toDoubleOrNull()?: 0.0,
            item["DESCRIPTION"]?: "",
            item["CATEGORY"]?: ""
        )

        transactions.add(t)
    }

    return transactions
}

