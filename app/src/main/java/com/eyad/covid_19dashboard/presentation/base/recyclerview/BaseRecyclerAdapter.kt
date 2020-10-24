package com.eyad.covid_19dashboard.presentation.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Recycler view item click listener
typealias ItemClickListener = ((view: View, position: Int) -> Unit)?

abstract class BaseRecyclerAdapter<V : BaseRecyclerViewHolder> : RecyclerView.Adapter<V>() {

    // Item click listener
    var itemClickListener: ItemClickListener = null

    // Attached recycler view
    protected var recyclerView: RecyclerView? = null

    // Context extracted from attached recycler view
    protected open var context: Context? = null

    // Ready to use layout inflater if needed
    protected open var inflater: LayoutInflater? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
        this.context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        return getViewHolder(parent, viewType, itemClickListener)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bind(position)
    }

    // ----

    /**
     * @return Constructed view holder
     */
    abstract fun getViewHolder(
        parent: ViewGroup,
        viewType: Int,
        itemClickListener: ItemClickListener
    ): V
}