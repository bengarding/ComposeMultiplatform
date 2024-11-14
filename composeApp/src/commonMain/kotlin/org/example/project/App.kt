package org.example.project

import Current
import WeatherData
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var weatherData: WeatherData? by remember { mutableStateOf(null) }
        var zipCode by remember { mutableStateOf("80550") }
        var textFieldIsError by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val keyboard = LocalSoftwareKeyboardController.current

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            TextField(
                value = zipCode,
                label = { Text("Zip Code") },
                onValueChange = { zipCode = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = textFieldIsError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            Button(
                onClick = {
                    if (zipCode.length == 5 && zipCode.toIntOrNull() != null) {
                        textFieldIsError = false
                        scope.launch {
                            weatherData = fetchWeatherData(zipCode)
                            keyboard?.hide()
                        }
                    } else {
                        textFieldIsError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
            ) {
                Text("Get weather info")
            }

            AnimatedVisibility(
                visible = weatherData != null,
            ) {
                WeatherInfo(weatherData!!)
            }

        }
    }
}

@Composable
private fun WeatherInfo(data: WeatherData) {
    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${data.location.name}, ${data.location.region}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(end = 4.dp)
            ) {
                AsyncImage(
                    model = data.current.condition.icon,
                    contentDescription = data.current.condition.text,
                    modifier = Modifier.fillMaxSize(),
                    onError = { error ->
                        Logger.e("${error.result}")
                    }
                )
            }
            Column {
                Text(
                    text = data.current.condition.text
                )
                Text(
                    text = "${data.current.temperatureInFahrenheit} °F"
                )
            }
        }

        DataTable(data.current)
    }
}

@Composable
private fun DataTable(data: Current) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        DataItem("Feels like", "${data.feelsLikeInFahrenheit} °F")
        DataItem("Wind chill", "${data.windChillInFahrenheit} °F")
        DataItem("Heat index", "${data.heatIndexInFahrenheit} °F")
        DataItem("Dew point", "${data.dewPointInFahrenheit} °F")
        DataItem("Wind speed", "${data.windSpeedInMph} MPH")
        DataItem("Wind gust", "${data.gustSpeedInMph} MPH")
        DataItem("Wind direction", data.windDirection)
        DataItem("Pressure", "${data.pressureInInches} inches")
        DataItem("Precipitation", "${data.precipitationInInches} inches")
        DataItem("Humidity", "${data.humidityPercentage}%")
        DataItem("Cloud cover", "${data.cloudCoverage}%")
        DataItem("UV Index", "${data.uvIndex}")
    }
}

@Composable
private fun DataItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f)
        )
    }
}
