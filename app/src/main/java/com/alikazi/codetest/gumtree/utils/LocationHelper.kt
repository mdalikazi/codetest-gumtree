package com.alikazi.codetest.gumtree.utils

import android.app.Activity
import android.content.IntentSender
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationHelper(private val activity: Activity) : LocationCallback() {

    val locationLiveData = MutableLiveData<Location>()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    override fun onLocationResult(result: LocationResult?) {
        result ?: return
        for (location in result.locations) {
            DLog.d("onLocationResult")
            locationLiveData.postValue(location)
            // Stop location updates after one hit.
            // We dont need constant updates.
            stopLocationUpdates()
        }
    }

    fun startLocationUpdates() {
        DLog.i("startLocationUpdates")
        checkLocationSettings()
    }

    fun stopLocationUpdates() {
        DLog.i("stopLocationUpdates")
        fusedLocationClient.removeLocationUpdates(this)
    }

    private fun checkLocationSettings() {
        DLog.i("checkLocationSettings")
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            DLog.d("All location settings are satisfied")
            fusedLocationClient.requestLocationUpdates(locationRequest, this, null)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    DLog.d("Location settings are not satisfied")
                    exception.startResolutionForResult(activity, Constants.REQUEST_CODE_LOCATION_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

}