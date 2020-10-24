package com.eyad.covid_19dashboard.presentation.news

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eyad.covid_19dashboard.R
import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.presentation.base.BaseFragment
import com.eyad.covid_19dashboard.presentation.base.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.toolbar
import java.net.UnknownHostException

class NewsFragment: BaseFragment(){

    override val layoutResId = R.layout.fragment_news

    private lateinit var viewModel: NewsViewModel

    override val dependencyInjectionEnabled = true

    private val args: NewsFragmentArgs by navArgs()

    private var adapter: NewsRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        toolbar.title = getString(R.string.lbl_news_title, args.country.name)

        adapter = NewsRecyclerAdapter()
        adapter?.itemClickListener = { _, position ->
            adapter?.getItemAt(position)?.let {
                findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(it))
            }
        }
        ContextCompat.getDrawable(requireContext(), R.drawable.news_line_devider)?.let{
            val dividerItemDecoration = DividerItemDecorator(it)
            rv_news.addItemDecoration(dividerItemDecoration)
        }

        rv_news.adapter = adapter

        adapter?.setOnLoadMoreTriggered {
            getNews((adapter?.itemCount!! / PAGE_SIZE) + 1)
        }
        str_news.setOnRefreshListener {
            adapter?.clearAll()
            getNews(1)
            str_news.isRefreshing = false
        }

        handleObservers()

        if (viewModel.articlesLiveData.value == null){
            getNews(1)
        }
    }

    private fun getNews(page: Int) {
        viewModel.getData(args.country.name, page)
    }

    private fun handleObservers() {
        viewModel.progressLiveData?.observe(viewLifecycleOwner, Observer {
            handleLoading(it)
        })

        viewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            handleArticlesLiveData(it)
        })

        viewModel.failureLiveData?.observe(viewLifecycleOwner, Observer {
            if (it is UnknownHostException){
                showToast(getString(R.string.lbl_no_internet_connection))
            }
            layout_empty.isVisible = true
        })
    }

    private fun handleArticlesLiveData(newsResponse: NewsResponse) {
        adapter?.apply{
            appendList(newsResponse.articles)
            if (itemCount >= newsResponse.totalResults){
                updateHasMoreItems(false)
                finishLoading()
                notifyDataSetChanged()
            }else{
                updateHasMoreItems(true)
                finishLoading()
            }
        }
    }

    companion object{
        private const val PAGE_SIZE = 20
    }
}