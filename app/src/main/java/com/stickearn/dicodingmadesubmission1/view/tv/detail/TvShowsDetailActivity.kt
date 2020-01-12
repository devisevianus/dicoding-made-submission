package com.stickearn.dicodingmadesubmission1.view.tv.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.convertDate
import com.stickearn.dicodingmadesubmission1.helper.convertToLong
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.view.tv.TvShowsViewModel
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

    private lateinit var mViewModel: TvShowsViewModel

    private var isChecked = false
    private var tvShows: TvShowsMdl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mViewModel = ViewModelProvider(this).get(TvShowsViewModel::class.java)
        tvShows = intent.getSerializableExtra(EXTRA_TV_SHOWS) as TvShowsMdl

        mViewModel.findTvShowFromFavorite(tvShows?.id!!)?.observe(this, Observer { tvShowsList ->
            if (tvShowsList != null && tvShowsList.isNotEmpty()) {
                if (tvShows?.id == tvShowsList[0].id) {
                    isChecked = true
                    toggle_favorite.isChecked = isChecked
                }
            }
        })

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
        toggle_favorite.isVisible = true
        toggle_favorite.setOnClickListener {
            isChecked = !isChecked
            actionTvShowsFavorite(isChecked)
        }
    }

    private fun initView() {
        try {
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/${tvShows?.poster}")
                .error(R.drawable.img_noposter)
                .into(iv_movie_poster)

            tv_movie_title.text = tvShows?.name
            if (tvShows?.first_air_date != null && tvShows?.first_air_date != "") {
                tv_movie_date.text = resources.getString(
                    R.string.placeholder_release_date,
                    tvShows?.first_air_date?.convertToLong()?.convertDate()
                )
            }
            tv_movie_rating.text = resources.getString(R.string.placeholder_rating, tvShows?.vote_average.toString())
            tv_movie_overview.text = tvShows?.overview
            rb_movies.max = 100
            rb_movies.progress = ((tvShows?.vote_average!! * 10).toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun actionTvShowsFavorite(isChecked: Boolean) {
        if (isChecked) {
            mViewModel.addTvShowToFavorite(tvShows!!)
            Toast.makeText(
                this,
                resources.getString(R.string.message_add_to_favorite),
                Toast.LENGTH_SHORT).show()
        } else {
            mViewModel.deleteTvFromFavorite(tvShows!!)
            Toast.makeText(
                this,
                resources.getString(R.string.message_remove_from_favorite),
                Toast.LENGTH_SHORT).show()
        }
    }

}