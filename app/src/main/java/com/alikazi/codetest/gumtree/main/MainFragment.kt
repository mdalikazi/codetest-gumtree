package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alikazi.codetest.gumtree.R
import com.alikazi.codetest.gumtree.utils.Injector
import com.alikazi.codetest.gumtree.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModelProviders shows deprecated but
        // the proposed replacement does not make sense
        // We need to define a lifecycle owner, in this case the MainFragment
        mainViewModel = ViewModelProviders.of(
            this,
            Injector.provideViewModelFactory())
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainFragmentProgressBar
        mainFragmentEmptyMessageTextView
    }
}