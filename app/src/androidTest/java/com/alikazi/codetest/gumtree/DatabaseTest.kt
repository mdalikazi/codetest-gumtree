package com.alikazi.codetest.gumtree

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.alikazi.codetest.gumtree.database.AppDatabase
import com.alikazi.codetest.gumtree.database.SearchHistoryDao
import com.alikazi.codetest.gumtree.database.WeatherDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.io.IOException

@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DatabaseTest {

    @get:Rule
    val executor = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var weatherDao: WeatherDao
    private lateinit var searchHistoryDao: SearchHistoryDao

    @Before
    fun openDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        weatherDao = database.weatherDao
        searchHistoryDao = database.searchHistoryDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testWeatherTable() {
        // Insert mock data
        runBlocking { weatherDao.insertCurrentWeather(getMockWeather()) }
        // Load data
        weatherDao.lastSearchedWeather.testingObserver {
            assert(it.name == getMockWeather().name)
        }
    }

    @Test
    fun testSearchHistoryTable() {
        // Insert mock data to search history
        runBlocking { searchHistoryDao.insertQueryInHistory(getMockSearchQuery()) }
        // Load data
        searchHistoryDao.searchHistory.testingObserver {
            assert(it[0].searchTerm == getMockSearchQuery().searchTerm)
        }
    }
}
