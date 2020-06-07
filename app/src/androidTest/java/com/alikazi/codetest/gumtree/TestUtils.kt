package com.alikazi.codetest.gumtree

import com.alikazi.codetest.gumtree.models.CurrentWeather
import com.alikazi.codetest.gumtree.models.Temperature
import com.alikazi.codetest.gumtree.models.Weather

fun getMockWeather(): CurrentWeather {
    val weather = Weather(
        id = 0,
        main = "Sunny",
        description = "Partly sunny",
        icon = "")
    val temperature = Temperature(
        temp = 285.3,
        humidity = 89.0,
        feelsLike = 280.9,
        tempMin = 275.5,
        tempMax = 290.1
    )
    return CurrentWeather(
        uuid = 1,
        name = "Amsterdam",
        weather = listOf(weather),
        temperature = temperature
    )
}