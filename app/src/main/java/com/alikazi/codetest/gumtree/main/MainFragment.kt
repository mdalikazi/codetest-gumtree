package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.models.SearchQuery
import com.alikazi.codetest.gumtree.utils.DLog
import com.alikazi.codetest.gumtree.utils.Injector
import com.alikazi.codetest.gumtree.utils.kelvinToCelcius
import com.alikazi.codetest.gumtree.utils.showSnackbar
import com.alikazi.codetest.gumtree.viewmodels.LocationViewModel
import com.alikazi.codetest.gumtree.viewmodels.SearchHistoryViewModel
import com.alikazi.codetest.gumtree.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.net.UnknownHostException
import java.util.*

@Suppress("DEPRECATION")
class MainFragment : Fragment(),
    MySearchView.SearchViewEventsListener,
    SearchHistoryRecyclerAdapter.SearchHistoryItemClickListener {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel
    private lateinit var locationViewModel: LocationViewModel

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWeatherViewModel()
        initSearchHistoryViewModel()
        initLocationViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @ExperimentalStdlibApi
    private fun initWeatherViewModel() {
        /**
         * Even though [androidx.lifecycle.ViewModelProviders] is deprecated, it must be used
         * because using [ViewModelProvider] takes activity context which
         * registers the ViewModel to both activity & fragment and
         * causes an extra trigger on observed LiveData.
         */
        // Using full class name to avoid deprecated warning in import
        weatherViewModel = androidx.lifecycle.ViewModelProviders.of(
            this,
            Injector.provideMyViewModelFactory(activity!!))
            .get(WeatherViewModel::class.java)

        weatherViewModel.isRefreshing.observe(this, Observer {
            mainFragmentProgressBar.visibility = processVisibility(it)
        })

        weatherViewModel.errors.observe(this, Observer {
            it.let {
                if (it is UnknownHostException) {
                    mainFragmentContainer.showSnackbar(getString(R.string.main_fragment_snackbar_message_offline))
                } else {
                    mainFragmentContainer.showSnackbar(it.toString())
                }
            }
        })

        weatherViewModel.lastSearchedWeather.observe(this, Observer {
            it?.let {
                weatherLocationName.text = it.name
                weatherDescription.text = it.weather[0].description.capitalize(Locale.getDefault())
                weatherTemperature.text = getString(
                    R.string.weather_temperature,
                    kelvinToCelcius(it.temperature.temp))
                weatherFeelsLike.text = getString(
                    R.string.weather_temperature_feels_like,
                    kelvinToCelcius(it.temperature.feelsLike))
                weatherTemperatureMinMax.text = getString(
                    R.string.weather_temperature_min_max,
                    kelvinToCelcius(it.temperature.tempMax),
                    kelvinToCelcius(it.temperature.tempMin))
            }
            weatherDetailsContainer.visibility = processVisibility(it != null)
            mainFragmentEmptyMessageTextView.visibility = processVisibility(it == null)
        })
    }

    private fun  initSearchHistoryViewModel() {
        searchHistoryViewModel = androidx.lifecycle.ViewModelProviders.of(
            this,
            Injector.provideMyViewModelFactory(activity!!))
            .get(SearchHistoryViewModel::class.java)
    }

    private fun initLocationViewModel() {
        activity?.let { activity ->
            DLog.i("initLocationViewModel")
            locationViewModel = androidx.lifecycle.ViewModelProviders.of(
                    activity,
                    Injector.provideLocationViewModelFactory(activity))
                    .get(LocationViewModel::class.java)
            locationViewModel.location.observe(this, Observer { location ->
                locationViewModel.setRefreshing(false)
                // TODO
                DLog.d("longitude ${location.longitude}")
                DLog.d("latitude ${location.latitude}")
            })

            locationViewModel.isRefreshing.observe(this, Observer {
                mainFragmentProgressBar.visibility = processVisibility(it)
            })
        }
    }

    override fun onSearchQuerySubmit(query: String) {
        weatherViewModel.fetchWeatherWithQuery(query)
        searchHistoryViewModel.saveQuery(SearchQuery(query))
    }

    override fun onSearchViewExpandedOrCollapsed(expanded: Boolean) {
        (activity as MainActivity).showHideSearchHistoryRecyclerView(expanded)
    }

    override fun onClickHistoricalQuery(searchQuery: SearchQuery) {
        (activity as MainActivity).onBackPressed()
        weatherViewModel.fetchWeatherWithQuery(searchQuery.searchTerm)
    }

    override fun onClickRemoveQuery(searchQuery: SearchQuery) {
        searchHistoryViewModel.deleteQuery(searchQuery)
    }

    private fun processVisibility(shouldShow: Boolean): Int = if (shouldShow) View.VISIBLE else View.GONE
}