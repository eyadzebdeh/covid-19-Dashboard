package com.eyad.covid_19dashboard.presentation.news

import androidx.lifecycle.MutableLiveData
import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.domain.usecase.GetCountryCodeUseCase
import com.eyad.covid_19dashboard.domain.usecase.GetNewsUseCase
import com.eyad.covid_19dashboard.presentation.base.BaseViewModel
import com.eyad.covid_19dashboard.presentation.base.SingleLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getCountryCodeUseCase: GetCountryCodeUseCase
) : BaseViewModel(){

    var articlesLiveData: MutableLiveData<NewsResponse> = MutableLiveData()

    fun getData(countryName: String, page: Int) {
        compositeDisposable.add(getCountryCodeUseCase.execute(countryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when(result){
                    is GetCountryCodeUseCase.Result.Success -> {
                        getNews(page, result.success)
                    }
                    is GetCountryCodeUseCase.Result.Failure -> {
                        showFailure(result.throwable)
                    }
                    is GetCountryCodeUseCase.Result.Loading -> {
                        showProgress()
                    }
                }
            })
    }

    fun getNews(page: Int, countryCode: String) {
        compositeDisposable.add(getNewsUseCase.execute(page, countryCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when(result){
                    is GetNewsUseCase.Result.Success -> {
                        hideProgress()
                        if (result.success.totalResults == 0){
                            showFailure(Throwable())
                        }else{
                            articlesLiveData.value = result.success
                        }
                    }
                    is GetNewsUseCase.Result.Failure -> {
                        showFailure(result.throwable)
                    }
                    is GetNewsUseCase.Result.Loading -> {
                        showProgress()
                    }
                }
            })
    }

}