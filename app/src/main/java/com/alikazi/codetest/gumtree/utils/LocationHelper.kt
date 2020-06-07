package com.alikazi.codetest.gumtree.utils

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener

class LocationHelper(context: Context) : LocationCallback() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
    val locationLiveData = MutableLiveData<Location>()

    override fun onLocationResult(result: LocationResult?) {
        result ?: return
        for (location in result.locations) {
            DLog.d("onLocationResult")
            locationLiveData.postValue(location)
        }
    }

    fun startLocationUpdates() {
        DLog.i("startLocationUpdates")
        /*fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                DLog.d("addOnSuccessListener")
                locationLiveData.postValue(it)
            }
        }*/
        fusedLocationClient.requestLocationUpdates(locationRequest, this, null)

    }

    fun stopLocationUpdates() {
        DLog.i("stopLocationUpdates")
        fusedLocationClient.removeLocationUpdates(this)
    }

}