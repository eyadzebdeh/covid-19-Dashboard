package com.eyad.covid_19dashboard.di

import androidx.lifecycle.ViewModel
import com.eyad.covid_19dashboard.di.annotation.ViewModelKey
import com.eyad.covid_19dashboard.di.factory.ViewModelFactory
import com.eyad.covid_19dashboard.di.factory.ViewModelFactoryImpl
import com.eyad.covid_19dashboard.presentation.map.CovidMapViewModel
import com.eyad.covid_19dashboard.presentation.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelsModule{

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactoryImpl): ViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(CovidMapViewModel::class)
    abstract fun bindCovidMapViewModelViewModel(viewModel: CovidMapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModelViewModel(viewModel: NewsViewModel): ViewModel
}