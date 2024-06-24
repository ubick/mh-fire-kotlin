package org.liviu.transaction

import kotlinx.datetime.LocalDate

class TransactionTransformer {
    fun fromList(list: MutableList<Map<String, String>>): List<Transaction> {
        val transactions = filterTransactions(list)

        return cleanCategories(transactions)
    }

    private fun cleanCategories(transactions: List<Transaction>): List<Transaction> {
        val categoriesMap = mapOf(
            "Telephone & mobile" to "Bank, Legal, Tax",
            "Service fees & bank charges" to "Bank, Legal, Tax",
            "Gifts to friends & family" to "Gifts",
            "Charitable giving" to "Gifts",
            "Fuel" to "Car",
            "Car running costs" to "Car",
            "Interest earnings" to "Income",
            "Rewards" to "Income",
            "Other income" to "Income",
            "Salary" to "Income",
            "Eating out" to "Restaurant",
            "Savings" to "Transfers",
            "Investments" to "Transfers",
            "Credit card payments" to "Transfers",
            "Securities trades" to "Transfers",
            "TV & internet" to "Bank, Legal, Tax",
            "Clothing & shoes" to "Clothing",
            "Entertainment" to "Experiences",
            "Child & dependent expenses" to "Kids",
            "Travel" to "Transport",
            "Healthcare" to "Personal care",
            "Home maintenance" to "Household",
            "Other household expenses" to "Household",
            "Home improvement" to "Household",
            "Taxes" to "Bank, Legal, Tax",
            "Cash withdrawals" to "ATM",
            "Household insurance" to "Household",
            "Electronics" to "Household",
            "Online services" to "Subscriptions",
            "General merchandise" to "Gifts",
            "Other business costs" to "Holiday"
        )

        return transactions.map {
            var category: String = it.category

            if (it.description.equals("BARCLAYS PRTNR FIN")) {
                category = "PhoneNetTV"
            }
            if (it.description.equals("OMNI CAPITAL RF")) {
                category = "Personal care"
            }

            category = categoriesMap.get(category) ?: category

            Transaction(
                it.date,
                it.amount,
                it.description,
                category
            )
        }
    }

    private fun filterTransactions(rawData: MutableList<Map<String, String>>): MutableList<Transaction> {
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
}