package com.example.myaddressesapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.example.myaddressesapp.R
import com.example.myaddressesapp.ui.UiConstants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun Activity.setTransparentStatusBar() {

}

fun View.bellAnimation() {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
}


fun AppCompatButton.disable() {
    this.text = UiConstants.DOWNLOADING
    this.isEnabled = false
}

fun AppCompatButton.enable() {
    this.text = UiConstants.ADD_TO_HISTORY
    this.isEnabled = true
}

fun GoogleMap.zoomPlus(level: Float) {
    animateCamera(
        CameraUpdateFactory.newLatLngZoom(
            cameraPosition.target,
            cameraPosition.zoom + level
        )
    )
}

fun GoogleMap.zoomMinus(level: Float) {
    animateCamera(
        CameraUpdateFactory.newLatLngZoom(
            cameraPosition.target,
            cameraPosition.zoom - level
        )
    )
}

fun GoogleMap.defineUserSelectedMapStyle(name: String, context: Context) {
    when (name) {
        UiConstants.DARK_MAP -> setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.night_map_style))
        UiConstants.NORMAL_MAP -> mapType = GoogleMap.MAP_TYPE_TERRAIN
        UiConstants.HYBRID_MAP -> mapType = GoogleMap.MAP_TYPE_HYBRID
        UiConstants.SATELLITE_MAP -> mapType = GoogleMap.MAP_TYPE_SATELLITE
    }
}


fun LinearLayout.collapse() {
    BottomSheetBehavior.from(this).apply {
        state = BottomSheetBehavior.STATE_COLLAPSED
        peekHeight = 200
    }
}

fun LinearLayout.expand() {
    BottomSheetBehavior.from(this).apply {
        state = BottomSheetBehavior.STATE_COLLAPSED
        peekHeight = 240
    }
}
