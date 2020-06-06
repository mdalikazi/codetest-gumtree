package com.alikazi.codetest.gumtree.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity
data class CurrentWeather(
    @PrimaryKey
    @NotNull
    val uuid: Int = 1,
    val name: String = "",
    val weather: List<Weather> = emptyList(),
    @SerializedName("main")
    val temperature: Temperature = Temperature())