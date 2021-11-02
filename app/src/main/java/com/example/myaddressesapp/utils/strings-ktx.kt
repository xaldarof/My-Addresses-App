package com.example.myaddressesapp.utils

import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import com.example.myaddressesapp.R
import com.example.myaddressesapp.ui.UiConstants


fun GoogleMap.formatToPosition(): String {
    return "${this.cameraPosition.target.latitude},${this.cameraPosition.target.longitude}"
}

fun TextView.copyText() {
    val clipboard: ClipboardManager? =
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(UiConstants.CLIP_BOARD, text.toString())
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, R.string.success_copy, Toast.LENGTH_SHORT).show()
}