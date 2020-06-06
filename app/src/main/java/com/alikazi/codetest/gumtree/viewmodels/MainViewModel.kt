package com.alikazi.codetest.gumtree.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alikazi.codetest.gumtree.network.Repository
import com.alikazi.codetest.gumtree.utils.DLog
import com.alikazi.codetest.gumtree.utils.isNumeric
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {


    val response = repository.response

    private val _errors = MutableLiveData<Exception>()
    val errors: LiveData<Exception> get() = _errors

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    fun fetchWeatherWithQuery(query: String) {
        fetchSomethingFromRepository {
            if (query.isNumeric() && query.length == 5) {
                // Its ZIP code
                DLog.d("Its zip code")
                repository.getWeatherByZipCode(query.toInt())
            } else {
                // Its City name
                DLog.d("Its city")
                repository.getWeatherByCity(query)
            }
        }
    }

    fun fetchWeatherWithLocation(location: Location) {
        fetchSomethingFromRepository {
            repository.getWeatherByLocation(location)
        }
    }

    /**
     * This is a higher order function which takes a block of code and
     * executes it on a coroutine scope. This helps us abstract the boilerplate
     * code that shows/hides a loader whenever we fetch from network and catches exceptions.
     */
    private fun fetchSomethingFromRepository(block: suspend() -> Unit): Job {
        return viewModelScope.launch {
            try {
                _isRefreshing.value = true
                block()
            } catch (e: Exception) {
                _errors.postValue(e)
                DLog.d("Exception ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

}