package com.eyad.covid_19dashboard.presentation.news

import android.view.LayoutInflater
import android.view.ViewGroup
import com.eyad.covid_19dashboard.BR
import com.eyad.covid_19dashboard.databinding.ItemArticleBinding
import com.eyad.covid_19dashboard.domain.model.Article
import com.eyad.covid_19dashboard.presentation.base.recyclerview.BaseRecyclerViewHolder
import com.eyad.covid_19dashboard.presentation.base.recyclerview.ItemClickListener
import com.eyad.covid_19dashboard.presentation.base.recyclerview.PaginationRecyclerAdapter

class NewsRecyclerAdapter(
    private val list: MutableList<Article> = mutableListOf()
) : PaginationRecyclerAdapter<BaseRecyclerViewHolder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int, itemClickListener: ItemClickListener):
            BaseRecyclerViewHolder {
        return ItemViewHolder(ItemArticleBinding.inflate(
            LayoutInflater.from(context), parent,
            false), itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun getItemAt(position: Int): Article? {
        return list.getOrNull(position)
    }

    fun appendList(list: List<Article>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAll() {
        list.clear()
    }

    private inner class ItemViewHolder(
        binding: ItemArticleBinding, itemClickListener: ItemClickListener) :
        BaseRecyclerViewHolder(binding, itemClickListener) {

        override fun bind(position: Int) {
            val article = getItemAt(position)
            bind<ItemArticleBinding> {
                setVariable(BR.article, article)
            }

        }
    }
}