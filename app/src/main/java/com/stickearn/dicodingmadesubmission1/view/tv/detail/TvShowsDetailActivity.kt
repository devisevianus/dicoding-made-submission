package com.stickearn.dicodingmadesubmission1.view.tv.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.convertDate
import com.stickearn.dicodingmadesubmission1.helper.convertToLong
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by devis on 2020-01-04
 */

class TvShowsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOWS = "extra_tv_shows"

        fun startThisActivity(context: Context, tvShowsMdl: TvShowsMdl) {
            val intent = Intent(context, TvShowsDetailActivity::class.java)
            intent.putExtra(EXTRA_TV_SHOWS, tvShowsMdl)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        tv_toolbar_title.text = resources.getString(R.string.title_tv_shows_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        try {
            val tvShows = intent.getSerializableExtra(EXTRA_TV_SHOWS) as TvShowsMdl

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/${tvShows.poster}")
                .into(iv_movie_poster)

            tv_movie_title.text = tvShows.name
            tv_movie_date.text = resources.getString(R.string.placeholder_release_date, tvShows.first_air_date.convertToLong()?.convertDate())
            tv_movie_rating.text = resources.getString(R.string.placeholder_rating, tvShows.vote_average.toString())
            tv_movie_overview.text = tvShows.overview
            rb_movies.max = 100
            rb_movies.progress = ((tvShows.vote_average * 10).toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}