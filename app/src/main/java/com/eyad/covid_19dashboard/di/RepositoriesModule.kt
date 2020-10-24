package com.eyad.covid_19dashboard.di

import android.content.Context
import com.eyad.covid_19dashboard.data.network.CovidApi
import com.eyad.covid_19dashboard.data.network.client.CountriesClient
import com.eyad.covid_19dashboard.data.network.client.NewsClient
import com.eyad.covid_19dashboard.data.network.client.TrackingClient
import com.eyad.covid_19dashboard.data.network.service.CountriesService
import com.eyad.covid_19dashboard.data.network.service.NewsService
import com.eyad.covid_19dashboard.data.network.service.TrackingService
import com.eyad.covid_19dashboard.data.repository.CovidRepositoryImpl
import com.eyad.covid_19dashboard.data.repository.config.AssetsRepository
import com.eyad.covid_19dashboard.data.repository.config.AssetsRepositoryImpl
import com.eyad.covid_19dashboard.domain.repository.CovidRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoriesModule{

    @Singleton
    @Provides
    fun provideTrackingService(trackingClient: TrackingClient): TrackingService {
        return trackingClient.build().create(TrackingService::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsService(newsClient: NewsClient): NewsService {
        return newsClient.build().create(NewsService::class.java)
    }

    @Singleton
    @Provides
    fun provideCountriesService(countriesClient: CountriesClient): CountriesService {
        return countriesClient.build().create(CountriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideCovidApi(trackingService: TrackingService, newsService: NewsService, countriesService: CountriesService) =
        CovidApi(trackingService, newsService, countriesService)

    @Provides
    @Singleton
    fun provideCovidRepository(covidApi: CovidApi): CovidRepository{
        return CovidRepositoryImpl(covidApi)
    }

    @Provides
    @Singleton
    fun provideAssetsRepository(context: Context, gson: Gson): AssetsRepository{
        return AssetsRepositoryImpl(context, gson)
    }


}