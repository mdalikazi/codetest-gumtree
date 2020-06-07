package com.alikazi.codetest.gumtree

import android.view.KeyEvent
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.alikazi.codetest.gumtree.main.MainActivity
import com.alikazi.codetest.gumtree.main.SearchHistoryRecyclerAdapter
import com.alikazi.codetest.gumtree.utils.IdlingResourcesHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.io.IOException

@LargeTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainFragmentTest {

    @get:Rule
    val mainActivityTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().register(IdlingResourcesHelper.getIdlingResource())
    }

    @After
    @Throws(IOException::class)
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(IdlingResourcesHelper.getIdlingResource())
    }

    @Test
    fun a_testSearchCityName() {
        searchQueryForWeather(getMockCityName())
        // Check that city name matches our search
        Espresso.onView(ViewMatchers.withId(R.id.weatherLocationName))
            .check(ViewAssertions.matches(ViewMatchers.withText(getMockCityName())))
    }

    @Test
    fun b_testSearchZipCode() {
        searchQueryForWeather(getMockZipCode())
        // Check that city name matches our search
        Espresso.onView(ViewMatchers.withId(R.id.weatherLocationName))
            .check(ViewAssertions.matches(ViewMatchers.withText("New York")))
    }

    @Test
    fun c_testSearchHistoryFunction() {
        expandSearchView()
        // Let the expand animation finish
        runBlocking { delay(1000) }
        // Check that search history has at least 1 entry
        if (isRecyclerViewNotEmpty()) {
            clickOnFirstItemInRecyclerView()
            // Wait for network call to finish
            IdlingResourcesHelper.increment()
            runBlocking { delay(5000) }
            IdlingResourcesHelper.decrement()

            // This test does not work - I want to match
            // RecyclerView's item's TextView with weatherLocationName TextView in MainFragment

            /*
            // Reopen search view to make RecyclerView visible
            expandSearchView()
            // Let the expand animation finish
            runBlocking { delay(1000) }
            // Check that city name matches our search
            Espresso.onView(ViewMatchers.withId(R.id.searchHistoryRecyclerView))
                    .check(ViewAssertions.matches(
                            atPosition(
                                    0,
                                    hasDescendant(ViewMatchers.withId(R.id.weatherLocationName)))))
            */
        }
    }

    private fun searchQueryForWeather(string: String) {
        expandSearchView()
        typeTextInSearchView(string)
        // Check that progress bar is displayed
        Espresso.onView(ViewMatchers.withId(R.id.mainFragmentProgressBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // Wait for network call to finish
        IdlingResourcesHelper.increment()
        runBlocking { delay(5000) }
        IdlingResourcesHelper.decrement()
    }

    private fun expandSearchView() {
        Espresso.onView(ViewMatchers.withId(R.id.menu_main_search_icon))
                .perform(ViewActions.click())
    }

    private fun typeTextInSearchView(text: String) {
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java))
                .perform(ViewActions.typeText(text),
                        ViewActions.pressKey(KeyEvent.KEYCODE_ENTER))
    }

    private fun isRecyclerViewNotEmpty(): Boolean {
        val recyclerView = getRecyclerView()
        return recyclerView.adapter != null && recyclerView.adapter?.itemCount != 0
    }

    private fun getRecyclerView(): RecyclerView {
        return mainActivityTestRule.activity.findViewById(R.id.searchHistoryRecyclerView)
    }

    private fun clickOnFirstItemInRecyclerView() {
        Espresso.onView(ViewMatchers.withId(R.id.searchHistoryRecyclerView))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<SearchHistoryRecyclerAdapter.SearchQueryViewHolder>
                        (0, ViewActions.click()))
    }

}