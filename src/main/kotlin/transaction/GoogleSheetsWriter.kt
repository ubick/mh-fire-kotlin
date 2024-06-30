package org.liviu.transaction

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import java.util.*

class GoogleSheetsWriter : TransactionWriter {
    private val APPLICATION_NAME = "Google Sheets API Kotlin Quickstart"
    private val CREDENTIALS_FILE_PATH = "./resources/credentials.json"
    private val JSON_FACTORY = GsonFactory.getDefaultInstance()

    companion object {
        private val properties = Properties().apply {
            load(GoogleSheetsWriter::class.java.getResourceAsStream("/config.properties"))
        }

        private val spreadsheetId = properties.getProperty("spreadsheetId")
        private val range = properties.getProperty("range")
    }

    private val service: Sheets by lazy {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credentials = GoogleCredentials.fromStream(FileInputStream(CREDENTIALS_FILE_PATH))
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets"))

        val requestInitializer: HttpRequestInitializer = HttpCredentialsAdapter(credentials)

        Sheets.Builder(httpTransport, JSON_FACTORY, requestInitializer)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    // @TODO: Update data format so that it only updates only 1 row instead of multiple
    override fun write(transactions: List<Transaction>) {
        // @noop: This function is only useful for debugging JSON output
    }

    override fun write(data: Map<String, Double>) {
        val values = data.values.toList()
        val valueRange = ValueRange().setValues(listOf(values))

        val request = service?.spreadsheets()?.values()?.update(spreadsheetId, range, valueRange)
            ?.setValueInputOption("RAW")

        request?.execute()
    }
}
