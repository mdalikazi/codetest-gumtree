package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.DLog
import com.alikazi.codetest.gumtree.utils.Injector
import com.alikazi.codetest.gumtree.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MySearchView.SearchViewEventsListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Even though [androidx.lifecycle.ViewModelProviders] is deprecated, it must be used
         * because using [ViewModelProvider] takes activity context which
         * registers the ViewModel to both activity & fragment and
         * causes an extra trigger on observed LiveData.
         */
        @Suppress("DEPRECATION")
        // Using full class name to avoid deprecated warning in import
        mainViewModel = androidx.lifecycle.ViewModelProviders.of(
            this,
            Injector.provideViewModelFactory())
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainFragmentProgressBar
        mainFragmentEmptyMessageTextView
    }

    override fun onSearchQuerySubmit(query: String) {
        DLog.d("search query $query")
    }
}