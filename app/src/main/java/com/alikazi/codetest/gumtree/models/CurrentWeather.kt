package com.alikazi.codetest.gumtree.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeather(
    @PrimaryKey
    val id: Int = 1,
    val weather: Weather,
    val temperature: Temperature)