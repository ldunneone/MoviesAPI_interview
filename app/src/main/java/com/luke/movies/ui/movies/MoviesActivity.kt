package com.luke.movies.ui.movies

import android.os.Bundle
import com.luke.movies.R
import com.luke.movies.base.BaseActivity

class MoviesActivity : com.luke.movies.base.BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(R.id.content, MoviesFragment(), true)
    }
}