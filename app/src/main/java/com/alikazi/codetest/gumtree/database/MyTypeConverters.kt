package com.alikazi.codetest.gumtree.database

import androidx.room.TypeConverter
import com.alikazi.codetest.gumtree.models.Temperature
import com.alikazi.codetest.gumtree.models.Weather
import com.google.gson.Gson

class MyTypeConverters {

    @TypeConverter
    fun convertStringToWeather(string: String): Weather {
        return Gson().fromJson(string, Weather::class.java)
    }

    @TypeConverter
    fun convertWeatherToString(src: Weather): String {
        return Gson().toJson(src)
    }

    @TypeConverter
    fun convertStringToTemperature(string: String): Temperature {
        return Gson().fromJson(string, Temperature::class.java)
    }

    @TypeConverter
    fun convertTemperatureToString(src: Temperature): String {
        return Gson().toJson(src)
    }

}