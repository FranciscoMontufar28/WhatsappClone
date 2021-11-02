package com.francisco.imagemanager

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import java.io.File


fun ImageView.loadUrlImage(context: Context, url: String) {
    Glide.with(context).load(url).fitCenter().placeholder(getProgressDrawable(context)).into(this)
}

fun ImageView.loadFileImage(context: Context, file: File) {
    Glide.with(context).asBitmap().load(file).fitCenter().placeholder(getProgressDrawable(context))
        .into(this)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.setDrawable(context: Context, image: Int) {
    val sdk = android.os.Build.VERSION.SDK_INT;
    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, image));
    } else {
        this.setImageDrawable(ContextCompat.getDrawable(context, image))
    }
}