package com.alikazi.codetest.gumtree.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alikazi.codetest.gumtree.network.Repository
import com.alikazi.codetest.gumtree.utils.isNumeric
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _errors = MutableLiveData<String>()
    val errors get() = _errors

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing get() = _isRefreshing

    fun fetchWeatherWithQuery(query: String) {
        fetchSomethingFromRepository {
            if (query.isNumeric() && query.length == 5) {
                // Its ZIP code
                repository.getWeatherByZipCode(query.toInt())
            } else {
                // Its City name
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
            if (_isRefreshing.value == false) {
                try {
                    _isRefreshing.value = true
                    block()
                } catch (e: Exception) {
                    _errors.value = e.message
                } finally {
                    _isRefreshing.value = false
                }
            }
        }
    }

}