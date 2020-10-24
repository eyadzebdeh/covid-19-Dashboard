package com.eyad.covid_19dashboard.binding

import android.widget.ImageView
import com.bumptech.glide.Glide

class BindingAdaptersImpl : BindingAdapters{
    override fun ImageView.loadImage(imageUrl: String?) {
        val request = Glide.with(this).load(imageUrl)
        request.into(this)
    }

}