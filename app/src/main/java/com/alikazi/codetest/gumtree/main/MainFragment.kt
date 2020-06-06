package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.DLog
import com.alikazi.codetest.gumtree.utils.Injector
import com.alikazi.codetest.gumtree.utils.showSnackbar
import com.alikazi.codetest.gumtree.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.UnknownHostException

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
            Injector.provideViewModelFactory(activity!!))
            .get(MainViewModel::class.java)

        mainViewModel.isRefreshing.observe(this, Observer {
            mainFragmentProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        mainViewModel.errors.observe(this, Observer {
            it.let {
                if (it is UnknownHostException) {
                    mainFragmentContainer.showSnackbar(getString(R.string.main_fragment_snackbar_message_offline))
                } else {
                    mainFragmentContainer.showSnackbar(it.toString())
                }
            }
        })

        mainViewModel.response.observe(this, Observer {
            it?.let {
                // TODO
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainFragmentProgressBar
        mainFragmentEmptyMessageTextView
    }

    override fun onSearchQuerySubmit(query: String) {
        mainViewModel.fetchWeatherWithQuery(query)
    }

    private fun showRefreshing() {

    }
}