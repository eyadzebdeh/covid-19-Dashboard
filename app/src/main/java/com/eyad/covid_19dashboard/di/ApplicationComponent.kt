package com.eyad.covid_19dashboard.di

import android.content.Context
import com.eyad.covid_19dashboard.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        FragmentsModule::class,
        ViewModelsModule::class,
        NetworkModule::class,
        RepositoriesModule::class,
        BindingModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {

    override fun inject(instance: App?)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}