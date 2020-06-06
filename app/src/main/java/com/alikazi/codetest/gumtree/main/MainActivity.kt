package com.alikazi.codetest.gumtree.main

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.Constants
import com.alikazi.codetest.gumtree.utils.DLog
import com.alikazi.codetest.gumtree.utils.showAlertDialog
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var customSearchView: MySearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        setupSearchViewAndRevealToolbar()
        Stetho.initializeWithDefaults(this)
        if (savedInstanceState == null) {
            goToMainFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main_search_icon -> {
            customSearchView.animateSearchView(revealToolbar.visibility != View.VISIBLE)
            customSearchView.getSearchMenuItem()?.expandActionView()
            true
        }
        R.id.menu_main_action_gps -> {
            checkLocationPermission()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchViewAndRevealToolbar() {
        customSearchView = MySearchView(this, revealToolbar)
        revealToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun goToMainFragment() {
        val fragment = MainFragment()
        customSearchView.setSearchViewEventsListener(fragment)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commitNow()
    }

    override fun onBackPressed() {
        if (revealToolbar.visibility == View.VISIBLE) {
            customSearchView.animateSearchView(false)
        } else {
            super.onBackPressed()
        }
    }

    private fun checkLocationPermission() {
        val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            onLocationPermissionGranted()
        } else {
            if (isLocationExplanationNeeded()) {
                showLocationExplanationDialog()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun onLocationPermissionGranted() {
        DLog.i("Location permission granted")
        // TODO
    }

    private fun showLocationExplanationDialog() {
        showAlertDialog(
            getString(R.string.permission_location_explanation_title),
            getString(R.string.permission_location_explanation_message),
            null,
            DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
                requestLocationPermission()
            },
            null,
            null)
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants.REQUEST_CODE_PERMISSION_COARSE_LOCATION)
    }

    private fun isLocationExplanationNeeded(): Boolean {
        return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            Constants.REQUEST_CODE_PERMISSION_COARSE_LOCATION -> {
                if (permissions.isNotEmpty() && permissions[0] == Manifest.permission.ACCESS_COARSE_LOCATION &&
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onLocationPermissionGranted()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
