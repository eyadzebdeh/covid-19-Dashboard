package com.eyad.covid_19dashboard

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.eyad.covid_19dashboard.binding.BindingAdapters
import com.eyad.covid_19dashboard.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication(){

    @Inject
    lateinit var bindingAdapters: BindingAdapters

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        setupDataBindingComponent()

    }

    private fun setupDataBindingComponent() {
        DataBindingUtil.setDefaultComponent(object : DataBindingComponent {
            override fun getBindingAdapters(): BindingAdapters {
                return this@App.bindingAdapters
            }

        })
    }

}