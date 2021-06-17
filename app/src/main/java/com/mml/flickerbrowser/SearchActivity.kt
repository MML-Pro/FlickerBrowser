package com.mml.flickerbrowser

import android.app.SearchManager
import android.app.SearchableInfo
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.preference.PreferenceManager
import com.mml.flickerbrowser.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchView:SearchView;
    
    companion object {
        private const val TAG = "SearchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.hide()
        activateToolbar(true)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: called")
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo:SearchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

//        Log.d(TAG, "onCreateOptionsMenu: $componentName")
//        Log.d(TAG, "onCreateOptionsMenu: hint is ${searchView.queryHint}")
//        Log.d(TAG, "onCreateOptionsMenu: $searchableInfo")

        searchView.isIconified = false

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: called")
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPreferences.edit().putString(FLICKER_QUERY,query).apply()
                searchView.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        searchView.setOnCloseListener {
            finish()
            false
        }

        Log.d(TAG, "onCreateOptionsMenu: returning")
        return true
    }
}