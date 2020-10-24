package com.eyad.covid_19dashboard.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected open var compositeDisposable = CompositeDisposable()

    var progressLiveData: MutableLiveData<Boolean>? = MutableLiveData()
        private set

    var failureLiveData: SingleLiveData<Throwable>? = SingleLiveData()
        private set

    fun showProgress() {
        progressLiveData?.value = true
    }

    fun hideProgress() {
        progressLiveData?.value = false
    }

    fun showFailure(throwable: Throwable) {
        hideProgress()
        failureLiveData?.value = throwable
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        progressLiveData = null
        failureLiveData = null
    }
}