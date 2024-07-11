package ru.itis.mygarden.data.api

import ru.itis.mygarden.data.Plant
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.json.JSONObject
import ru.itis.mygarden.exception.PlantNotFoundException
import java.util.Locale

class ApiPlantInfoHandler(private val plantName: String) {

    private val API_KEY = "sk-b4pv6686c3a4ce1566141"
    val url = "https://perenual.com/api/species-list?key=$API_KEY&q=$plantName"
    val descriptionHandler = ApiPlantDescriptionHandler(plantName)




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

    suspend fun fetchPlantFromJson(): Plant {
        val jsonObject = JSONObject(fetchJsonFromUrl(url))
        val dataArray = jsonObject.optJSONArray("data") ?:
                    throw PlantNotFoundException("Plant with given name not found")

        if (dataArray.length() == 0) throw PlantNotFoundException("Plant with given name not found")

        val firstElement = dataArray.getJSONObject(0)

        val watering = firstElement?.optString("watering", "")
        val wateringFrequency = when (watering?.lowercase(Locale.ROOT)) {
            "frequent" -> 1
            "average" -> 2
            "minimum" -> 7
            "upgrade plans to premium/supreme - https://perenual.com/subscription-api-pricing. i'm sorry" -> -1
            else -> 2
        }
        val nextWateringTime = wateringFrequency.let {
            System.currentTimeMillis() + it * 86400 * 1000
        }

        val sunlightArray = firstElement?.optJSONArray("sunlight")
        val sunlight = sunlightArray?.optString(0, "") ?: "no info"
        val defaultImage = firstElement?.optJSONObject("default_image")
        val regularUrl = defaultImage?.optString("regular_url", "") ?: ""

        val plant = Plant(
            id = 0,
            name = plantName,
            description = descriptionHandler.fetchDescriptionFromJson(),
            sunlight = sunlight,
            nextWateringTime = nextWateringTime,
            wateringFrequency = wateringFrequency ?: 0,
            imgSource = regularUrl
        )
        return plant
    }

}