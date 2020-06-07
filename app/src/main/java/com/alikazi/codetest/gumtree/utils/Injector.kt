package com.alikazi.codetest.gumtree.utils

import android.content.Context
import com.alikazi.codetest.gumtree.database.AppDatabase
import com.alikazi.codetest.gumtree.network.Repository
import com.alikazi.codetest.gumtree.viewmodels.LocationViewModelFactory
import com.alikazi.codetest.gumtree.viewmodels.MyViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient

object Injector {

    fun provideMyViewModelFactory(context: Context): MyViewModelFactory {
        return MyViewModelFactory(provideRepository(context))
    }

    private fun provideRepository(context: Context): Repository {
        return Repository(provideAppDatabase(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideLocationViewModelFactory(context: Context): LocationViewModelFactory {
        return LocationViewModelFactory(context)
    }

}