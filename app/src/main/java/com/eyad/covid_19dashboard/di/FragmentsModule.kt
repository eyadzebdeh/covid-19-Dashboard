package com.eyad.covid_19dashboard.di

import com.eyad.covid_19dashboard.presentation.map.CovidMapFragment
import com.eyad.covid_19dashboard.presentation.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun bindCovidMapFragment(): CovidMapFragment

    @ContributesAndroidInjector
    abstract fun bindNewsFragment(): NewsFragment

}