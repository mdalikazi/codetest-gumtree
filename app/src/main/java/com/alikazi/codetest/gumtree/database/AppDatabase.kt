package com.alikazi.codetest.gumtree.database

import android.content.Context
import androidx.room.*
import com.alikazi.codetest.gumtree.models.CurrentWeather

@TypeConverters(MyTypeConverters::class)
@Database(entities = [CurrentWeather::class], version = 5, exportSchema = false)
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