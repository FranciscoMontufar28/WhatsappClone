package com.francisco.imagemanager

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import java.io.File


fun ImageView.loadUrlImage(context: Context, url: String) {
    Glide.with(context).load(url).centerCrop().placeholder(getProgressDrawable(context)).into(this)
}

fun ImageView.loadFileImage(context: Context, file: File) {
    Glide.with(context).asBitmap().load(file).centerCrop().placeholder(getProgressDrawable(context))
        .into(this)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}