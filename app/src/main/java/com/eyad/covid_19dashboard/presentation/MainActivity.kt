package com.eyad.covid_19dashboard.presentation

import android.os.Bundle
import com.eyad.covid_19dashboard.R
import com.eyad.covid_19dashboard.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override val dependencyInjectionEnabled: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}