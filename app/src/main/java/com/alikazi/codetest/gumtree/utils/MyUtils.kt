package com.alikazi.codetest.gumtree.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.alikazi.codetest.gumtree.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = ContextCompat.getSystemService(
        this, ConnectivityManager::class.java) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Context.circularReveal(viewToReveal: View, posFromRight: Int,
                           containsOverflow: Boolean, isShow: Boolean) {
    var width = viewToReveal.width
    if (posFromRight > 0) {
        width -= posFromRight *
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) -
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) /
                2
    }

    if (containsOverflow) {
        width -= resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
    }

    val cx = width
    val cy = viewToReveal.height / 2

    val anim: Animator = when(isShow) {
        true -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, 0f, width.toFloat())
        else -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, width.toFloat(), 0f)
    }

    anim.duration = 400

    // make the view invisible when the animation is finished
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            if (!isShow) {
                viewToReveal.visibility = View.INVISIBLE
            }
        }
    })

    // make the view visible and start the animation
    if (isShow) {
        viewToReveal.visibility = View.VISIBLE
    }

    anim.start()
}