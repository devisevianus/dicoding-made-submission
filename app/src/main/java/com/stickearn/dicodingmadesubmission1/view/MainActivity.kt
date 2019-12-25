package com.stickearn.dicodingmadesubmission1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.list.MovieAdapter
import com.stickearn.dicodingmadesubmission1.view.movie.detail.MovieDetailActivity
import com.stickearn.dicodingmadesubmission1.view.movie.list.MovieFragment
import com.stickearn.dicodingmadesubmission1.view.tv.TvShowsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_toolbar_title.text = resources.getString(R.string.title_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pagerAdapter = MainAdapter(supportFragmentManager)
        pagerAdapter.add(MovieFragment.newInstance(), "Movies")
        pagerAdapter.add(TvShowsFragment.newInstance(), "TV Shows")

        vp_content.adapter = pagerAdapter
        tablayout_content.setupWithViewPager(vp_content)

    }


}
