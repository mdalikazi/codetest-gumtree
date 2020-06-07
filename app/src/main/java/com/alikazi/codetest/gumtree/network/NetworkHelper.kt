package com.alikazi.codetest.gumtree.network

import android.location.Location
import android.net.Uri
import com.alikazi.codetest.gumtree.models.CurrentWeather
import com.alikazi.codetest.gumtree.utils.Constants
import com.alikazi.codetest.gumtree.utils.DLog
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.net.URI
import java.net.URL
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

object NetworkHelper {

    private val service: Network by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Network::class.java)
    }

    fun getNetworkService() = service

    interface Network {
        @GET
        suspend fun fetchWeatherByCity(@Url cityUrl: String): CurrentWeather

        @GET
        suspend fun fetchWeatherByZipCode(@Url zipUrl: String): CurrentWeather

        @GET
        suspend fun fetchWeatherByLatLon(@Url locationUrl: String): CurrentWeather
    }

    fun getCityWeatherUrl(cityName: String): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_CITY, cityName)

        val url = URL(builder.build().toString()).toString()
        DLog.d("url $url")
        return url
    }

    fun getZipCodeWeatherUrl(zipCode: Int): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_ZIP, zipCode.toString())

        val url = URL(builder.build().toString()).toString()
        DLog.d("url $url")
        return url
    }

    fun getLatLonWeatherUrl(location: Location): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_LAT, roundToTwoDecimalPoints(location.latitude))
            .appendQueryParameter(Constants.URL_QUERY_LON, roundToTwoDecimalPoints(location.longitude))

        val url = URL(builder.build().toString()).toString()
        DLog.d("url $url")
        return url
    }

    private fun getBuilderWithBaseUrl(): Uri.Builder =
        Uri.parse(Constants.URL_BASE)
            .buildUpon()
            .appendPath(Constants.URL_PATH_WEATHER)
            .appendQueryParameter(Constants.URL_QUERY_API_KEY, Constants.API_KEY)

    private fun roundToTwoDecimalPoints(double: Double): String = DecimalFormat(Constants.DECIMAL_FORMAT).format(double)

}