package org.liviu

import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionTransformerTest {

    @Test
    fun testFromListValidInput() {
        val inputData = listOf(
            mapOf("DATE" to "2024-01-01", "AMOUNT" to "100.0", "DESCRIPTION" to "Groceries", "CATEGORY" to "Food"),
            mapOf("DATE" to "2024-02-01", "AMOUNT" to "50.0", "DESCRIPTION" to "Internet Bill", "CATEGORY" to "TV & internet")
        ).toMutableList()

        val expectedOutput = listOf(
            Transaction(LocalDate(2024, 1, 1), 100.0, "Groceries", "Food"),
            Transaction(LocalDate(2024, 2, 1), 50.0, "Internet Bill", "Bank, Legal, Tax")
        )

        val transformer = TransactionTransformer()
        val result = transformer.fromList(inputData)

        assertEquals(expectedOutput, result)
    }
}
