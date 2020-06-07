package com.alikazi.codetest.gumtree.viewmodels

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alikazi.codetest.gumtree.utils.LocationHelper

class LocationViewModel(context: Context) : ViewModel() {

    private val locationHelper = LocationHelper(context)

    val location: LiveData<Location> get() = locationHelper.locationLiveData

    fun getLocation() {
        locationHelper.startLocationUpdates()
    }

    override fun onCleared() {
        super.onCleared()
        locationHelper.stopLocationUpdates()
    }
}