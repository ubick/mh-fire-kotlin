package org.liviu.transaction

import kotlinx.datetime.LocalDate

class TransactionTransformer {
    fun aggregateToCategories(transactions: List<Transaction>): Map<String, Double> {
        val categories: MutableMap<String, Double> = mutableMapOf(
            "Bank, Legal, Tax" to 0.0,
            "Groceries" to 0.0,
            "Transport" to 0.0,
            "Car" to 0.0,
            "PhoneNetTV" to 0.0,
            "Utilities" to 0.0,
            "Kids" to 0.0,
            "Experiences" to 0.0,
            "Restaurant" to 0.0,
            "Clothing" to 0.0,
            "Household" to 0.0,
            "Hobbies" to 0.0,
            "ATM" to 0.0,
            "Subscriptions" to 0.0,
            "Personal care" to 0.0,
            "Gifts" to 0.0,
            "Holiday" to 0.0,
            "Income" to 0.0,
            "Transfers" to 0.0,
            "Uncategorized" to 0.0
        )

        for (transaction in transactions) {
            val currentValue = categories.getOrDefault(transaction.category, 0.0)
            categories[transaction.category] = currentValue + Math.abs(transaction.amount)
        }

        val categoriesToExclude = setOf("Uncategorized", "Transfers", "Income")

        return categories.filterNot { categoriesToExclude.contains(it.key) }
    }

    fun fromList(list: MutableList<Map<String, String>>): List<Transaction> {
        val transactions = cleanCategories(filterTransactions(list))

        return transactions
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