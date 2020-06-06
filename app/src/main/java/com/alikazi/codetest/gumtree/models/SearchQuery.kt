package com.alikazi.codetest.gumtree.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class SearchQuery(
    @PrimaryKey
    @NotNull
    val searchTerm: String = "")