package com.alikazi.codetest.gumtree.database

import android.content.Context
import androidx.room.*
import com.alikazi.codetest.gumtree.models.CurrentWeather
import com.alikazi.codetest.gumtree.models.SearchQuery

@TypeConverters(MyTypeConverters::class)
@Database(entities = [CurrentWeather::class, SearchQuery::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
    abstract val searchHistoryDao: SearchHistoryDao

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