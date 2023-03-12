package com.valkotova.presenter.di

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.valkotova.presenter.R

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
            .circleCrop()
            .into(this)
    }
}