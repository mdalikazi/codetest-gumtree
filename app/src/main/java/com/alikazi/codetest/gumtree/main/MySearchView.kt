package com.alikazi.codetest.gumtree.main

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.Constants
import com.alikazi.codetest.gumtree.utils.circularRevealAnimation

class MySearchView(private val activity: Activity,
                   private val revealToolbar: Toolbar) {

    fun setSearchViewEventsListener(listener: SearchViewEventsListener) {
        searchViewEventsListener = listener
    }

    init {
        initSearchView()
    }

    private lateinit var searchView: SearchView
    private lateinit var searchMenuItem: MenuItem
    private var searchViewEventsListener: SearchViewEventsListener? = null

    private fun initSearchView() {
        revealToolbar.inflateMenu(R.menu.menu_search)
        searchMenuItem = revealToolbar.menu.findItem(R.id.menu_action_search)
        searchView = searchMenuItem.actionView as SearchView
        searchView.apply {

            // Associate searchable configuration with the SearchView
            val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))

            inputType = (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d(Constants.LOG_TAG, "onQueryTextChange newText: $query")
                    searchViewEventsListener?.onSearchQuerySubmit(query)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    // Disallow, we have no use of this right now
                    return false
                }
            })
        }

        searchMenuItem.apply {
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
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
    }

    fun animateSearchView(reveal: Boolean) {
        activity.circularRevealAnimation(revealToolbar, 0, true, reveal)
    }

    fun getSearchMenuItem(): MenuItem? {
        return searchMenuItem
    }

    interface SearchViewEventsListener {
        fun onSearchQuerySubmit(query: String)
    }

}