package com.eyad.covid_19dashboard.presentation.map

import androidx.lifecycle.MutableLiveData
import com.eyad.covid_19dashboard.domain.model.CountryTrackingInfo
import com.eyad.covid_19dashboard.domain.usecase.GetPolygonsForCountries
import com.eyad.covid_19dashboard.domain.usecase.GetTrackingInfoUseCase
import com.eyad.covid_19dashboard.presentation.base.BaseViewModel
import com.eyad.covid_19dashboard.presentation.base.SingleLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CovidMapViewModel @Inject constructor(
    private val getTrackingInfoUseCase: GetTrackingInfoUseCase,
    private val getPolygonsForCountries: GetPolygonsForCountries
): BaseViewModel(){

    var trackingInfoLiveData: MutableLiveData<Map<String, CountryTrackingInfo>> = MutableLiveData()
    var date: MutableLiveData<Date> = MutableLiveData()
    var emptyLiveData: SingleLiveData<Unit> = SingleLiveData()

    init {
        date.value = Date()
    }

    fun getTrackingInfo(){
        val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date.value ?: Date())
        compositeDisposable.add(getTrackingInfoUseCase.execute(dateString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result){
                    is GetTrackingInfoUseCase.Result.Success -> {
                        val trackingInfoCountries = result.success.dates[dateString]
                        trackingInfoCountries?.let{
                            val countries = trackingInfoCountries.countries
                            if (countries.isNotEmpty()){
                                getPolygonsInfo(countries)
                            }else{
                                hideProgress()
                                emptyLiveData.value = Unit
                            }
                        } ?: let{
                            hideProgress()
                            emptyLiveData.value = Unit
                        }
                    }
                    is GetTrackingInfoUseCase.Result.Failure -> {
                        showFailure(result.throwable)
                    }
                    is GetTrackingInfoUseCase.Result.Loading -> {
                        showProgress()
                    }
                }
            })
    }

    private fun getPolygonsInfo(countries: Map<String, CountryTrackingInfo>) {
        compositeDisposable.add(getPolygonsForCountries.execute(countries)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when(result){
                    is GetPolygonsForCountries.Result.Success -> {
                        trackingInfoLiveData.value = getDangerLevel(result.success)
                        hideProgress()
                    }
                    is GetPolygonsForCountries.Result.Failure -> {
                        showFailure(result.throwable)
                    }
                    is GetPolygonsForCountries.Result.Loading -> {
                        showProgress()
                    }
                }
            })
    }

    private fun getDangerLevel(countries: Map<String, CountryTrackingInfo>): Map<String, CountryTrackingInfo>? {
        val sum = countries.values.toMutableList().sumBy { it.todayOpenCases }
        val average = sum / countries.size
        for (country in countries.values){
            country.dangerLevel = when {
                country.todayOpenCases < average / 2 -> 1
                country.todayOpenCases < average -> 2
                country.todayOpenCases < average * 1.5 -> 3
                else -> 4
            }
        }
        return countries
    }

    fun getCountryTrackingInfo(id: String): CountryTrackingInfo?{
        return trackingInfoLiveData.value?.get(id)
    }

}