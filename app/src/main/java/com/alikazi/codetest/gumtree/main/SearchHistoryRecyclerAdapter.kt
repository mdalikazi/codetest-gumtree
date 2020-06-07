package com.alikazi.codetest.gumtree.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.models.SearchQuery

class SearchHistoryRecyclerAdapter(context: Context) :
    ListAdapter<SearchQuery, SearchHistoryRecyclerAdapter.SearchQueryViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SearchQuery>() {
            override fun areItemsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
                return oldItem.searchTerm == newItem.searchTerm
            }
        }
    }

    private val inflater = LayoutInflater.from(context)
    private var queryClickListener: SearchHistoryItemClickListener? = null

    fun setSearchHisoryItemClickListener(clickListener: SearchHistoryItemClickListener) {
        queryClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchQueryViewHolder {
        val view = inflater.inflate(R.layout.recycler_item_query, parent, false)
        return SearchQueryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchQueryViewHolder, position: Int) {
        val searchQuery = getItem(position)
        holder.queryTextView.text = searchQuery.searchTerm
        holder.itemView.setOnClickListener {
            queryClickListener?.onClickHistoricalQuery(searchQuery)
        }
        holder.queryRemoveButton.setOnClickListener {
            queryClickListener?.onClickRemoveQuery(searchQuery)
        }
    }

    inner class SearchQueryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val queryTextView: TextView = itemView.findViewById(R.id.queryTextView)
        val queryRemoveButton: ImageView = itemView.findViewById(R.id.queryRemoveImageView)
    }

    interface SearchHistoryItemClickListener {

        fun onClickHistoricalQuery(searchQuery: SearchQuery)

        fun onClickRemoveQuery(searchQuery: SearchQuery)

    }

}