package com.amin.newalbumstask.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

fun String.getImageUrlForGlide(): GlideUrl {
    return GlideUrl(this, LazyHeaders.Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build()
    )
}

fun ImageView.setImageWithGlide(context: Context, sourceUrl:GlideUrl){
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(sourceUrl)
        .placeholder(circularProgressDrawable)
        .into(this)
}