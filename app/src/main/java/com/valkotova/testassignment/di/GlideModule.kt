package com.valkotova.testassignment.di

import android.content.Context
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.valkotova.testassignment.R
import java.net.URI
import java.net.URL

@GlideModule
class WishBoxAppGlideModule : AppGlideModule()

fun ImageView.loadGlide(uri : String) {
    Glide.with(this.context).load(uri).let {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        it.thumbnail()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(circularProgressDrawable)
            .error(R.mipmap.ic_launcher_round)
            .centerCrop()
            .into(this)
    }
}

fun ImageView.loadGlideCircled(uri : String) {
    Glide.with(this.context).load(uri).let {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        it.thumbnail()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(circularProgressDrawable)
            .error(R.mipmap.ic_launcher_round)
            .circleCrop()
            .into(this)
    }
}