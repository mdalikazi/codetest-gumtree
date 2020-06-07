package com.alikazi.codetest.gumtree

import androidx.room.Room
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alikazi.codetest.gumtree.database.AppDatabase
import com.alikazi.codetest.gumtree.database.WeatherDao
import com.alikazi.codetest.gumtree.main.MainActivity
import com.alikazi.codetest.gumtree.utils.IdlingResourcesHelper
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.runBlocking
import org.junit.*

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.runners.MethodSorters
import java.io.IOException

@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainFragmentTest {

    private lateinit var database: AppDatabase
    private lateinit var weatherDao: WeatherDao

    @get:Rule
    val mainActivityTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().register(mainActivityTestRule.activity.getIdlingResource())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        weatherDao = database.weatherDao
    }

    @After
    @Throws(IOException::class)
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(mainActivityTestRule.activity.getIdlingResource())
        database.close()
    }

    @Test
    fun checkIfLastWeatherLoaded() {
        runBlocking { weatherDao.insertCurrentWeather(getMockWeather()) }
        val lastSearchedWeather = database.weatherDao.lastSearchedWeather
        lastSearchedWeather.value?.let {
            Espresso.onView(ViewMatchers.withId(R.id.weatherLocationName))
                    .check(ViewAssertions.matches(
                            ViewMatchers.withText(it.name)))

        }
    }



}
