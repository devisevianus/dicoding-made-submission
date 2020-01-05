package com.stickearn.dicodingmadesubmission1.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.view.movie.list.MovieFragment
import com.stickearn.dicodingmadesubmission1.view.tv.list.TvShowsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_toolbar_title.text = resources.getString(R.string.title_app)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pagerAdapter =
            MainAdapter(supportFragmentManager)
        pagerAdapter.add(MovieFragment.newInstance(), resources.getString(R.string.title_movies))
        pagerAdapter.add(TvShowsFragment.newInstance(), resources.getString(R.string.title_tv_shows))

        vp_content.adapter = pagerAdapter
        tablayout_content.setupWithViewPager(vp_content)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
