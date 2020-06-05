package com.alikazi.codetest.gumtree.network

import android.location.Location
import android.net.Uri
import com.alikazi.codetest.gumtree.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Network::class.java)
    }

    interface Network {
        @GET
        suspend fun fetchWeather(): String
    }

    fun getCityWeatherUrl(cityName: String): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_CITY, cityName)

        return URL(builder.build().toString()).toString()
    }

    fun getZipCodeWeatherUrl(zipCode: Int): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_ZIP, zipCode.toString())

        return URL(builder.build().toString()).toString()
    }

    fun getLatLonWeatherUrl(location: Location): String {
        val builder = getBuilderWithBaseUrl()
            .appendQueryParameter(Constants.URL_QUERY_LAT, roundToTwoDecimalPoints(location.latitude))
            .appendQueryParameter(Constants.URL_QUERY_LON, roundToTwoDecimalPoints(location.longitude))

        return URL(builder.build().toString()).toString()
    }

    private fun getBuilderWithBaseUrl(): Uri.Builder =
        Uri.Builder()
            .scheme(Constants.URL_SCHEME_HTTPS)
            .authority(Constants.URL_AUTHORITY)
            .appendPath(Constants.URL_PATH_DATA)
            .appendPath(Constants.URL_PATH_CURRENT_WEATHER)
            .appendQueryParameter(Constants.URL_QUERY_API_KEY, Constants.API_KEY)

    private fun roundToTwoDecimalPoints(double: Double): String = DecimalFormat(Constants.DECIMAL_FORMAT).format(double)

}