package com.example.myaddressesapp.data.utils

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import com.example.myaddressesapp.R
import com.example.myaddressesapp.ui.UiConstants
import com.google.android.gms.maps.model.LatLng

fun Activity.setTransparentStatusBar() {

}

fun View.bellAnimation() {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
}

fun LatLng.isValid(): Boolean = this.latitude > 0f && this.longitude > 0f

fun AppCompatButton.disable() {
    this.text = UiConstants.DOWNLOADING
    this.isEnabled = false
}

fun AppCompatButton.enable() {
    this.text = UiConstants.ADD_TO_HISTORY
    this.isEnabled = true
}
