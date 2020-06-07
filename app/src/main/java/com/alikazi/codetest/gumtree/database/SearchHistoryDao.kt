package com.alikazi.codetest.gumtree.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alikazi.codetest.gumtree.models.SearchQuery

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueryInHistory(query: SearchQuery)

    @Query("DELETE FROM SearchQuery WHERE searchTerm = :searchTerm")
    suspend fun deleteQueryFromHistory(searchTerm: String)

    @get:Transaction
    @get:Query("SELECT * FROM SearchQuery")
    val searchHistory: LiveData<List<SearchQuery>>

}