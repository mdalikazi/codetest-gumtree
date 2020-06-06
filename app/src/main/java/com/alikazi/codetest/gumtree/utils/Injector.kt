package com.alikazi.codetest.gumtree.utils

import android.content.Context
import com.alikazi.codetest.gumtree.database.AppDatabase
import com.alikazi.codetest.gumtree.network.Repository
import com.alikazi.codetest.gumtree.viewmodels.MyViewModelFactory

object Injector {

    fun provideViewModelFactory(context: Context): MyViewModelFactory {
        return MyViewModelFactory(provideRepository(context))
    }

    private fun provideRepository(context: Context): Repository {
        return Repository(provideAppDatabase(context))
    }

    private fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

}