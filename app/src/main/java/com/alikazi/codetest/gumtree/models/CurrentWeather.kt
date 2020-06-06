package com.alikazi.codetest.gumtree.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class CurrentWeather(
    @PrimaryKey
    @NotNull
    val id: Int = 1,
    val weather: List<Weather> = emptyList(),
    val temperature: Temperature = Temperature()
                         )