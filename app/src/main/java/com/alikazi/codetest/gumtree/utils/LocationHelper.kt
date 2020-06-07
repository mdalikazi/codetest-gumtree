package com.alikazi.codetest.gumtree.utils

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

class LocationHelper(context: Context) : LocationCallback() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
    var location = MutableLiveData<Location>()

    override fun onLocationResult(result: LocationResult?) {
        result ?: return
        for (location in result.locations) {
            // TODO RETURN LOCATION
            this.location.postValue(location)
        }
    }

    fun startLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                // TODO RETURN LOCATION
                this.location.postValue(it)
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, this, null)

    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(this)
    }
    
}