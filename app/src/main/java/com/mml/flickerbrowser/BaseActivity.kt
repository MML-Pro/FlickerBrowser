package com.mml.flickerbrowser

import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "BaseActivity"
        internal const val FLICKER_QUERY = "FLICKER_QUERY"
        internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
    }

    internal fun activateToolbar(enableHome:Boolean){
        val toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)

    }
}