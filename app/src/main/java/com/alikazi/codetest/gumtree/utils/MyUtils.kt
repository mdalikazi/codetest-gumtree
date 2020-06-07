package com.alikazi.codetest.gumtree.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewAnimationUtils
import com.alikazi.codetest.gumtree.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun String.isNumeric(): Boolean {
    try {
        this.toInt()
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}

fun kelvinToCelcius(kelvin: Double): Int = (kelvin - 273.15).toInt()

fun Context.showAlertDialog(title: String?, message: String,
                            okText: String?, okClickListener: DialogInterface.OnClickListener?,
                            cancelText: String?, cancelClickListener: DialogInterface.OnClickListener?) {
    val builder = AlertDialog.Builder(this, R.style.AppTheme_DialogOverlay)
    builder.setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okText ?: this.getString(R.string.ok),
                    okClickListener ?: DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
            .setNegativeButton(cancelText ?: this.getString(R.string.cancel),
                    cancelClickListener ?: DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
            .create()
            .show()
}

fun View.performFadeOutFadeInAnimation(block: () -> Unit) {
    val abc = AnimatorSet()
    abc.setTarget(this)
    val fadeIn = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f)
            .setDuration(Constants.DEFAULT_ANIMATION_DURATION)
    val fadeOut = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f)
            .setDuration(Constants.DEFAULT_ANIMATION_DURATION)
    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            block()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationStart(animation: Animator?) {

        }
    })
    abc.playSequentially(fadeOut, fadeIn)
    abc.start()
}

fun Context.circularRevealAnimation(viewToReveal: View, posFromRight: Int,
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
    val anim: Animator = when (isShow) {
        true -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, 0f, width.toFloat())
        else -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, width.toFloat(), 0f)
    }

    anim.duration = Constants.DEFAULT_ANIMATION_DURATION
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