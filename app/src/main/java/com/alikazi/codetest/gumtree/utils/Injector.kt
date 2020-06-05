package com.alikazi.codetest.gumtree.utils

import com.alikazi.codetest.gumtree.network.Repository
import com.alikazi.codetest.gumtree.viewmodels.MyViewModelFactory

object Injector {

    fun provideViewModelFactory(): MyViewModelFactory {
        return MyViewModelFactory(provideRepository())
    }

    private fun provideRepository(): Repository {
        return Repository()
    }

}