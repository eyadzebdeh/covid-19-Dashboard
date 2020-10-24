package com.eyad.covid_19dashboard.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

interface BindingAdapters {

    @BindingAdapter(requireAll = false, value = ["imageUrl"])
    fun ImageView.loadImage(imageUrl: String?)

}