package org.liviu.transaction

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val date: LocalDate,
    val amount: Double,
    val description: String,
    val category: String
)
