package com.example.myaddressesapp.utils

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatButton
import com.example.myaddressesapp.R
import com.example.myaddressesapp.ui.UiConstants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
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

fun GoogleMap.zoomPlus(level:Float){
    animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition.target, cameraPosition.zoom+level))
}
fun GoogleMap.zoomMinus(level:Float){
    animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition.target, cameraPosition.zoom-level))
}
