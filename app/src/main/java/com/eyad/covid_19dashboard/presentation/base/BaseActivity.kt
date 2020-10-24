package com.eyad.covid_19dashboard.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eyad.covid_19dashboard.di.factory.ViewModelFactory
import com.eyad.covid_19dashboard.presentation.dialog.LoadingProgressDialog
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected open val dependencyInjectionEnabled = false

    private var loadingProgressBar: LoadingProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        handleDependencyInjection()
        super.onCreate(savedInstanceState)
    }

    private fun handleDependencyInjection() {
        if (dependencyInjectionEnabled) {
            AndroidInjection.inject(this)
        }
    }

    private fun showLoading() {
        if (loadingProgressBar == null) {
            loadingProgressBar = LoadingProgressDialog(this)
        }

        if (loadingProgressBar?.isShowing == false) {
            loadingProgressBar?.show()
        }
    }

    private fun hideLoading() {
        loadingProgressBar?.hide()
    }

    protected fun handleLoading(loadingState: Boolean) {
        if (loadingState) {
            showLoading()
        } else {
            hideLoading()
        }
    }


    override fun onDestroy() {
        loadingProgressBar?.cancel()
        loadingProgressBar = null
        super.onDestroy()
    }
}