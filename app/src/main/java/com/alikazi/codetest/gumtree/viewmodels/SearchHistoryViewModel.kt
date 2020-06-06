package com.alikazi.codetest.gumtree.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alikazi.codetest.gumtree.models.SearchQuery
import com.alikazi.codetest.gumtree.network.Repository
import kotlinx.coroutines.launch

class SearchHistoryViewModel(private val repository: Repository) : ViewModel() {

    val searchHistory = repository.searchHistoryFromDb

    fun saveQuery(query: SearchQuery) {
        viewModelScope.launch {
            repository.saveSearchQueryToDatabase(query)
        }
    }

    fun deleteQuery(query: SearchQuery) {
        viewModelScope.launch {
            repository.deleteSearchQueryFromDatabase(query)
        }
    }

}