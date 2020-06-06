package com.alikazi.codetest.gumtree.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alikazi.codetest.gumtree.models.CurrentWeather

@Database(entities = [CurrentWeather::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao

    companion object {
        private const val databaseName = "WeatherApp.db"
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        databaseName)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

    }

}