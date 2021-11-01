package com.example.myaddressesapp.data.utils

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import com.example.myaddressesapp.R

fun Activity.setTransparentStatusBar() {

}

fun View.bellAnimation() {
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
}
