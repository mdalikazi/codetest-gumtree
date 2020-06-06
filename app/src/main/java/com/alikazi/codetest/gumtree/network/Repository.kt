package com.alikazi.codetest.gumtree.network

import android.location.Location
import androidx.lifecycle.map
import com.alikazi.codetest.gumtree.database.AppDatabase
import com.alikazi.codetest.gumtree.models.CurrentWeather
import com.alikazi.codetest.gumtree.models.SearchQuery
import com.alikazi.codetest.gumtree.utils.DLog

class Repository(private val database: AppDatabase) {

    val lastSearchedWeatherFromDb = database.weatherDao.lastSearchedWeather.map { it }
    val searchHistoryFromDb = database.searchHistoryDao.searchHistory.map { it }

    suspend fun getWeatherByCity(cityName: String) {
        DLog.i("getWeatherByCity")
        val url = NetworkHelper.getCityWeatherUrl(cityName)
        val weather = NetworkHelper.getNetworkService().fetchWeatherByCity(url)
        saveWeatherToDatabase(weather)
    }

    suspend fun getWeatherByZipCode(zipCode: Int) {
        DLog.i("getWeatherByZipCode")
        val url = NetworkHelper.getZipCodeWeatherUrl(zipCode)
        val weather = NetworkHelper.getNetworkService().fetchWeatherByZipCode(url)
        saveWeatherToDatabase(weather)
    }

    suspend fun getWeatherByLocation(location: Location) {
        DLog.i("getWeatherByLocation")
        val url = NetworkHelper.getLatLonWeatherUrl(location)
        val weather = NetworkHelper.getNetworkService().fetchWeatherByLatLon(url)
        saveWeatherToDatabase(weather)
    }

    private suspend fun saveWeatherToDatabase(weather: CurrentWeather) {
//        DLog.d("weather $weather")
        database.weatherDao.insertCurrentWeather(weather)
    }

    suspend fun saveSearchQueryToDatabase(searchQuery: SearchQuery) {
        database.searchHistoryDao.insertQueryInHistory(searchQuery)
    }

    suspend fun deleteSearchQueryFromDatabase(searchQuery: SearchQuery) {
        database.searchHistoryDao.deleteQueryFromHistory(searchQuery.searchTerm)
    }

}