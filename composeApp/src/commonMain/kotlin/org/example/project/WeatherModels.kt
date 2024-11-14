import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val location: Location,
    val current: Current
)

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String,

    @SerialName("lat")
    val latitude: Float,

    @SerialName("lon")
    val longitude: Float,

    @SerialName("tz_id")
    val timeZoneId: String,

    @SerialName("localtime_epoch")
    val localTimeEpoch: Long,

    @SerialName("localtime")
    val localTime: String
)

@Serializable
data class Current(
    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Long,

    @SerialName("last_updated")
    val lastUpdated: String,

    @SerialName("temp_c")
    val temperatureInCelsius: Float,

    @SerialName("temp_f")
    val temperatureInFahrenheit: Float,

    @SerialName("is_day")
    val isDay: Int,

    val condition: Condition,

    @SerialName("wind_mph")
    val windSpeedInMph: Float,

    @SerialName("wind_kph")
    val windSpeedInKph: Float,

    @SerialName("wind_degree")
    val windDegree: Int,

    @SerialName("wind_dir")
    val windDirection: String,

    @SerialName("pressure_mb")
    val pressureInMb: Float,

    @SerialName("pressure_in")
    val pressureInInches: Float,

    @SerialName("precip_mm")
    val precipitationInMillimeters: Float,

    @SerialName("precip_in")
    val precipitationInInches: Float,

    @SerialName("humidity")
    val humidityPercentage: Int,

    @SerialName("cloud")
    val cloudCoverage: Int,

    @SerialName("feelslike_c")
    val feelsLikeInCelsius: Float,

    @SerialName("feelslike_f")
    val feelsLikeInFahrenheit: Float,

    @SerialName("windchill_c")
    val windChillInCelsius: Float,

    @SerialName("windchill_f")
    val windChillInFahrenheit: Float,

    @SerialName("heatindex_c")
    val heatIndexInCelsius: Float,

    @SerialName("heatindex_f")
    val heatIndexInFahrenheit: Float,

    @SerialName("dewpoint_c")
    val dewPointInCelsius: Float,

    @SerialName("dewpoint_f")
    val dewPointInFahrenheit: Float,

    @SerialName("vis_km")
    val visibilityInKilometers: Float,

    @SerialName("vis_miles")
    val visibilityInMiles: Float,

    @SerialName("uv")
    val uvIndex: Float,

    @SerialName("gust_mph")
    val gustSpeedInMph: Float,

    @SerialName("gust_kph")
    val gustSpeedInKph: Float
)

@Serializable
data class Condition(
    val text: String,
    @SerialName("icon") private val _icon: String,
    val code: Int
) {
    val icon: String
        get() = "https:$_icon"
}
