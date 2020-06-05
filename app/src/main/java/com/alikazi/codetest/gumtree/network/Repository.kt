package com.alikazi.codetest.gumtree.network

import android.location.Location
import com.alikazi.codetest.gumtree.utils.DLog

class Repository {

    suspend fun getWeatherByCity(cityName: String) {
        val url = NetworkHelper.getCityWeatherUrl(cityName)
        val string = NetworkHelper.getNetworkService().fetchWeatherByCity(url)
        DLog.d("string $string")
    }

    suspend fun getWeatherByZipCode(zipCode: Int) {
        val url = NetworkHelper.getZipCodeWeatherUrl(zipCode)
        val string = NetworkHelper.getNetworkService().fetchWeatherByZipCode(url)
        DLog.d("string $string")
    }

    suspend fun getWeatherByLocation(location: Location) {
        val url = NetworkHelper.getLatLonWeatherUrl(location)
        val string = NetworkHelper.getNetworkService().fetchWeatherByLatLon(url)
        DLog.d("string $string")
    }

}