package com.eyad.covid_19dashboard.presentation.dialog

import android.content.Context
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.eyad.covid_19dashboard.R
import kotlinx.android.synthetic.main.layout_progress_dialog.*

class LoadingProgressDialog(
    context: Context?
) : AppCompatDialog(context, R.style.DialogLoadingStyle) {

    init {
        setCancelable(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_dialog)

        setOnShowListener {
            context?.let {
                Glide.with(imageViewLoader)
                    .load(R.drawable.animated_logo)
                    .into(imageViewLoader)
            }
        }
    }
}