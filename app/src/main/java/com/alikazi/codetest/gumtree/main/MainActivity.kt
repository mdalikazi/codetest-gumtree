package com.alikazi.codetest.gumtree.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.alikazi.codetest.gumtree.R
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var customSearchView: MySearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        setupSearchViewAndRevealToolbar()
        Stetho.initializeWithDefaults(this)
        if (savedInstanceState == null) {
            goToMainFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_main_search_icon -> {
            customSearchView.animateSearchView(revealToolbar.visibility != View.VISIBLE)
            customSearchView.getSearchMenuItem()?.expandActionView()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchViewAndRevealToolbar() {
        customSearchView = MySearchView(this, revealToolbar)
        revealToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun goToMainFragment() {
        val fragment = MainFragment()
        customSearchView.setSearchViewEventsListener(fragment)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commitNow()
    }

    override fun onBackPressed() {
        if (revealToolbar.visibility == View.VISIBLE) {
            customSearchView.animateSearchView(false)
        } else {
            super.onBackPressed()
        }
    }
}
