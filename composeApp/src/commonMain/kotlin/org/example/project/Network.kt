package org.example.project

import WeatherData
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

expect val client: HttpClient

suspend fun fetchWeatherData(location: String): WeatherData? {
    return try {
        // Fetch the response as HttpResponse and read its content
        val response: HttpResponse = client.get("https://api.weatherapi.com/v1/current" +
                ".json?key=f1b7a78e32b74d2e9eb164014241311&q=$location")
        val responseBody: String = response.bodyAsText()  // Convert the response body to a String
        Logger.d(responseBody)

        // Check if response is not empty and parse it
        if (responseBody.isNotEmpty()) {
            Json.decodeFromString<WeatherData>(responseBody)
        } else {
            Logger.d { "Empty response from API" }
            null
        }
    } catch (e: Exception) {
        // Log any errors
        Logger.e(e) { "Error fetching weather data" }
        null
    }
}
