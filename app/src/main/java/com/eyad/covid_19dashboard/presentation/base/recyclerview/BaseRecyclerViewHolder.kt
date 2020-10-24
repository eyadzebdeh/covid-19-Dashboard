package com.eyad.covid_19dashboard.presentation.base.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewHolder(
    view: View,
    private val itemClickListener: ItemClickListener = null,
) : RecyclerView.ViewHolder(view) {

    var viewDataBinding: ViewDataBinding? = null
        private set

    constructor(
        viewDataBinding: ViewDataBinding,
        itemClickListener: ItemClickListener = null,
    ) : this(
        viewDataBinding.root,
        itemClickListener
    ) {
        this.viewDataBinding = viewDataBinding
    }

    init {
        if (itemClickListener != null) {
            handleItemClicks(itemView)
        }
    }

    private fun handleItemClicks(view: View) {
        view.setOnClickListener{
            invokeClickEvent(view)
        }
    }

    // ----

    /**
     * Apply data binding bind operation
     */
    @Suppress("UNCHECKED_CAST")
    protected inline fun <T : ViewDataBinding> bind(binding: T.() -> Unit) {
        binding(viewDataBinding as T)
        viewDataBinding?.executePendingBindings()
    }

    /**
     * Invoke [itemClickListener] with clicked position check.
     * Prevent out of bound indices
     */
    protected fun invokeClickEvent(view: View) {
        val clickedPosition = adapterPosition
        if (clickedPosition != RecyclerView.NO_POSITION) {
            itemClickListener?.invoke(view, clickedPosition)
        }
    }

    /**
     * Recycler view view binding operation
     *
     * @param position Iem position
     */
    abstract fun bind(position: Int)
}