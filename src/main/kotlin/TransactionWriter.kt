package org.liviu

interface TransactionWriter {
    fun write(transactions: List<Transaction>)
}