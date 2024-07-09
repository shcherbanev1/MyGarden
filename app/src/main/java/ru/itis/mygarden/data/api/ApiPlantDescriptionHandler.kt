package ru.itis.mygarden.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.json.JSONObject

class ApiPlantDescriptionHandler(private val plantName: String) {

    private val API_KEY = "sk-b4pv6686c3a4ce1566141"
    val url = "https://perenual.com/api/species-care-guide-list?key=$API_KEY&q=$plantName"

    private suspend fun fetchJsonFromUrl(url: String): String {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        return try {
            client.get(url).body()
        } finally {
            client.close()
        }
    }

    suspend fun fetchDescriptionFromJson(): String {
        val jsonObject = JSONObject(fetchJsonFromUrl(url))
        val dataArray = jsonObject.optJSONArray("data") ?: return ""
        if (dataArray.length() == 0) return ""
        val firstElement = dataArray.getJSONObject(0)

        val sectionArray = firstElement.optJSONArray("section")
        val firstDescription = sectionArray?.getJSONObject(0)?.optString("description").toString()
        return firstDescription
    }

}