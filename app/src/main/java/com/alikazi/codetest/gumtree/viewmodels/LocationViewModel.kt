package com.alikazi.codetest.gumtree.viewmodels

import android.app.Activity
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alikazi.codetest.gumtree.utils.LocationHelper

class LocationViewModel(activity: Activity) : ViewModel() {

    private val locationHelper = LocationHelper(activity)

    val location: LiveData<Location> get() = locationHelper.locationLiveData

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing get() = _isRefreshing

    fun getLocation() {
        setRefreshing(true)
        locationHelper.startLocationUpdates()
    }

    fun setRefreshing(isRefreshing: Boolean) {
        _isRefreshing.value = isRefreshing
    }

    override fun onCleared() {
        super.onCleared()
        locationHelper.stopLocationUpdates()
    }
}