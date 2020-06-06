package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.Injector
import com.alikazi.codetest.gumtree.utils.kelvinToCelcius
import com.alikazi.codetest.gumtree.utils.showSnackbar
import com.alikazi.codetest.gumtree.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.UnknownHostException
import java.util.*

class MainFragment : Fragment(), MySearchView.SearchViewEventsListener {

    private lateinit var mainViewModel: MainViewModel

    @ExperimentalStdlibApi
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
            mainFragmentProgressBar.visibility = processVisibility(it)
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
                weatherLocationName.text = it.name
                weatherDescription.text = it.weather[0].description.capitalize(Locale.getDefault())
                weatherTemperature.text = getString(
                    R.string.weather_temperature,
                    kelvinToCelcius(it.temperature.temp))
                weatherTemperatureMinMax.text = getString(
                    R.string.weather_temperature_min_max,
                    kelvinToCelcius(it.temperature.tempMin),
                    kelvinToCelcius(it.temperature.tempMax))
            }
            weatherDetailsContainer.visibility = processVisibility(it != null)
            mainFragmentEmptyMessageTextView.visibility = processVisibility(it == null)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onSearchQuerySubmit(query: String) {
        mainViewModel.fetchWeatherWithQuery(query)
    }

    private fun processVisibility(shouldShow: Boolean): Int = if (shouldShow) View.VISIBLE else View.GONE
}