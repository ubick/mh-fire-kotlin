package org.liviu.transaction

interface TransactionWriter {
    fun write(transactions: List<Transaction>)
}