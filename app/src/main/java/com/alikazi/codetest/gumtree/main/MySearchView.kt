package com.alikazi.codetest.gumtree.main

import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.Constants
import com.alikazi.codetest.gumtree.utils.circularReveal

class MySearchView(private val context: Context,
//                   private val revealAppBar: AppBarLayout,
                   private val revealToolbar: Toolbar) {

    companion object {
        private var searchViewEventsListener: SearchViewEventsListener? = null

        fun setSearchViewEventsListener(searchViewEventsListener: SearchViewEventsListener) {
            Companion.searchViewEventsListener = searchViewEventsListener
        }
    }

    init {
        initSearchView()
    }

    private var searchView: SearchView? = null
    private var searchMenuItem: MenuItem? = null

    private fun initSearchView() {
        revealToolbar.inflateMenu(R.menu.menu_search)
        searchMenuItem = revealToolbar.menu.findItem(R.id.menu_action_search)
        searchView = searchMenuItem?.actionView as SearchView?
        searchView?.inputType =
            (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(Constants.LOG_TAG, "onQueryTextChange newText: $query")
                searchViewEventsListener?.onSearchQuerySubmit(query)
                searchView?.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Disallow, we have no use of this right now
                return false
            }
        })

        searchMenuItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                animateSearchView(true)
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                animateSearchView(false)
                return false
            }
        })
    }

    fun animateSearchView(reveal: Boolean) {
        context.circularReveal(revealToolbar, 0, true, reveal)
    }

    fun getSearchMenuItem(): MenuItem? {
        return searchMenuItem
    }

    interface SearchViewEventsListener {
        fun onSearchQuerySubmit(query: String)
    }

}