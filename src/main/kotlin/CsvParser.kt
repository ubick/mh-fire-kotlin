package org.liviu

import java.io.BufferedReader
import java.io.FileReader
class CsvParser {
    fun parse(fileName: String): MutableList<Map<String, String>> {
        val data = mutableListOf<Map<String, String>>()
        BufferedReader(FileReader(fileName)).use { br ->
            val headers = br.readLine().split(",")
            br.lines().forEach { line ->
                val fields = parseLine(line)
                val row = headers.zip(fields).associate { it.first to it.second.trim() }
                data.add(row)
            }
        }
        return data
    }

    private fun parseLine(line: String): List<String> {
        val fields = mutableListOf<String>()
        var currentField = StringBuilder()
        var isInQuotes = false

        for (char in line) {
            when {
                char == '"' -> isInQuotes = !isInQuotes
                char == ',' && !isInQuotes -> {
                    fields.add(currentField.toString())
                    currentField = StringBuilder()
                }
                else -> currentField.append(char)
            }
        }

        if (currentField.isNotEmpty()) {
            fields.add(currentField.toString())
        }

        return fields
    }
}
