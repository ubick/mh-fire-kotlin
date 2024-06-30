package org.liviu.transaction

interface TransactionWriter {
    fun write(transactions: List<Transaction>)
    fun write(data: Map<String, Double>)
}