package com.example.myaddressesapp.utils

import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import com.example.myaddressesapp.R
import com.example.myaddressesapp.ui.UiConstants
import android.os.Vibrator
import android.view.View
import android.content.Intent
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity




fun GoogleMap.formatToPosition(): String {
    return "${this.cameraPosition.target.latitude},${this.cameraPosition.target.longitude}"
}


fun TextView.copyText() {
    val clipboard: ClipboardManager? = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    val clip = ClipData.newPlainText(UiConstants.CLIP_BOARD, text.toString())
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, R.string.success_copy, Toast.LENGTH_SHORT).show()
    context.vibrate()
}


fun Context.vibrate(){
    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    v!!.vibrate(20)
}

fun Context.shareText(text:String){
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,text.toString())
    startActivity(Intent.createChooser(intent, "Share location"))
}