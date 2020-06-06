package com.alikazi.codetest.gumtree.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alikazi.codetest.gumtree.models.CurrentWeather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather)

//    @Query("SELECT * FROM CurrentWeather")
//    suspend fun getLastSearchedWeather(): LiveData<CurrentWeather>

    @get:Transaction
    @get:Query("SELECT * FROM currentweather")
    val lastSearchedWeather: LiveData<CurrentWeather>

}