package com.eyad.covid_19dashboard.di

import com.eyad.covid_19dashboard.binding.BindingAdapters
import com.eyad.covid_19dashboard.binding.BindingAdaptersImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object BindingModule{

    @Provides
    @Singleton
    fun provideBindingAdapter(): BindingAdapters {
        return BindingAdaptersImpl()
    }

}