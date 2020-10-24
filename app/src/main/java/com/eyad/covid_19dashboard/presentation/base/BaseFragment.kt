package com.eyad.covid_19dashboard.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eyad.covid_19dashboard.di.factory.ViewModelFactory
import com.eyad.covid_19dashboard.presentation.dialog.LoadingProgressDialog
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment: Fragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected abstract val layoutResId: Int

    protected open val dependencyInjectionEnabled: Boolean = false

    private var loadingProgressBar: LoadingProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        handleDependencyInjection()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onDestroy() {
        loadingProgressBar?.cancel()
        loadingProgressBar = null
        super.onDestroy()
    }

    private fun handleDependencyInjection() {
        if (dependencyInjectionEnabled){
            AndroidSupportInjection.inject(this)
        }
    }

    private fun showLoading() {
        if (loadingProgressBar == null) {
            loadingProgressBar = LoadingProgressDialog(context)
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

    fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}