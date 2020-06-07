package com.alikazi.codetest.gumtree

import android.view.View
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.alikazi.codetest.gumtree.models.CurrentWeather
import com.alikazi.codetest.gumtree.models.SearchQuery
import com.alikazi.codetest.gumtree.models.Temperature
import com.alikazi.codetest.gumtree.models.Weather
import org.hamcrest.Description
import org.hamcrest.Matcher

fun getMockWeather(): CurrentWeather {
    val weather = Weather(
        id = 0,
        main = "Sunny",
        description = "Partly sunny",
        icon = "")
    val temperature = Temperature(
        temp = 285.3,
        humidity = 89.0,
        feelsLike = 280.9,
        tempMin = 275.5,
        tempMax = 290.1
    )
    return CurrentWeather(
        uuid = 1,
        name = "Amsterdam",
        weather = listOf(weather),
        temperature = temperature
    )
}

fun getMockCityName(): String = "Miami"

fun getMockZipCode(): String = "10021"

fun getMockSearchQuery(): SearchQuery {
    return SearchQuery("Wichita")
}

fun <T> LiveData<T>.testingObserver(onChangeHandler: (T) -> Unit) {
    val observer = OneTimeObserver(handler = onChangeHandler)
    observe(observer, observer)
}

fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher?.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            return itemMatcher?.matches(viewHolder.itemView)
        }
    }
}