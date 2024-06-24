package org.liviu

import java.io.BufferedReader
import java.io.FileReader

class CsvParser {
    fun parse(fileName: String): MutableList<Map<String, String>> {
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
}