package com.stickearn.dicodingmadesubmission1.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.stickearn.dicodingmadesubmission1.MovieBannerWidget
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.util.replaceFragment
import com.stickearn.dicodingmadesubmission1.view.favorite.FavoriteFragment
import com.stickearn.dicodingmadesubmission1.view.movie.list.MovieFragment
import com.stickearn.dicodingmadesubmission1.view.tv.list.TvShowsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG_MOVIES = "Movies"
        private const val TAG_FILM = "Film"
        private const val TAG_FAVORITES = "Favorites"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initBottomNavigation()

        if (savedInstanceState == null) {
            replaceFragment(MovieFragment.newInstance(), R.id.container, TAG_MOVIES)
        }

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

    private fun initToolbar() {
        tv_toolbar_title.text = resources.getString(R.string.title_app)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initBottomNavigation() {
        bnv_menu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movies -> {
                    replaceFragment(MovieFragment.newInstance(), R.id.container, TAG_MOVIES)
                    item.isChecked = true
                }
                R.id.navigation_tv_shows -> {
                    replaceFragment(TvShowsFragment.newInstance(), R.id.container, TAG_FILM)
                    item.isChecked = true
                }
                R.id.navigation_favorite -> {
                    replaceFragment(FavoriteFragment.newInstance(), R.id.container, TAG_FAVORITES)
                    item.isChecked = true
                }
            }

            false
        }
    }

}
