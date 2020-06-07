package com.alikazi.codetest.gumtree.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResourcesHelper {

    private const val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun getIdlingResource(): IdlingResource {
        return countingIdlingResource
    }

}