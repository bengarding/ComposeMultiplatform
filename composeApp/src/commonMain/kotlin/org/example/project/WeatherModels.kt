package org.example.project

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val location: Location,
    val current: Current
)

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String
)

@Serializable
data class Current(
    val temp_c: Double,
    val condition: Condition
)

@Serializable
data class Condition(
    val text: String,
    val icon: String
)
