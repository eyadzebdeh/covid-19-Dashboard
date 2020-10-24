package com.eyad.covid_19dashboard.presentation.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.paginate.recycler.LoadingListItemCreator

typealias OnLoadMoreTriggered = ((nextCursor: String?) -> Unit)?

abstract class PaginationRecyclerAdapter<V : BaseRecyclerViewHolder> : BaseRecyclerAdapter<V>(),
    Paginate.Callbacks {

    private var paginate: Paginate? = null

    private var onLoadMoreTriggered: OnLoadMoreTriggered = null

    private var hasMoreData: Boolean = false

    private var isContentLoading: Boolean = false

    protected open val loadMoreItemCreator: LoadingListItemCreator? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        setupPagination()

        // Hide load more recycler item at first time
        // Calling screen will handle this
        paginate?.setHasMoreDataToLoad(false)
    }

    override fun onLoadMore() {
        if (hasMoreData) {
            isContentLoading = true
            onLoadMoreTriggered?.invoke(null)
        }else{
            paginate?.setHasMoreDataToLoad(false)
        }
    }

    override fun isLoading(): Boolean {
        return isContentLoading
    }

    override fun hasLoadedAllItems(): Boolean {
        return !hasMoreData
    }

    /**
     *  Update loading status
     */
    fun finishLoading() {
        isContentLoading = false
    }

    fun updateHasMoreItems(hasMoreData: Boolean){
        this.hasMoreData = hasMoreData
    }

    fun setOnLoadMoreTriggered(onLoadMoreTriggered: OnLoadMoreTriggered) {
        this.onLoadMoreTriggered = onLoadMoreTriggered
    }

    // ----

    private fun setupPagination() {
        val builder = Paginate.with(recyclerView, this)
            .setLoadingTriggerThreshold(3)

        loadMoreItemCreator?.let { creator ->
            builder.setLoadingListItemCreator(creator)
        }

        paginate = builder.build()
    }

    // ----

    companion object {
        /**
         * Blank page indicator, other pages are either null or non-null values
         */
        const val CURSOR_FIRST_PAGE = ""
    }
}