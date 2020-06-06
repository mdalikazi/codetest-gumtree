package com.alikazi.codetest.gumtree.database

import androidx.room.TypeConverter
import com.alikazi.codetest.gumtree.models.Temperature
import com.alikazi.codetest.gumtree.models.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MyTypeConverters {

    @TypeConverter
    fun convertStringToWeather(string: String): List<Weather> {
        val type = object : TypeToken<List<Weather>>(){}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun convertWeatherToString(src: List<Weather>): String {
        val type = object : TypeToken<List<Weather>>(){}.type
        return Gson().toJson(src, type)
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