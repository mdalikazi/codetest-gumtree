package com.alikazi.codetest.gumtree.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather()

    @Query("SELECT * FROM CurrentWeather")
    fun getLastSearchedWeather()

}