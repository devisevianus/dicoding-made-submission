package com.stickearn.dicodingmadesubmission1.view.movie.detail

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by devis on 2019-12-08
 */

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"

        fun startThisActivity(context: Context, data: MovieMdl) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, data)
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
        tv_toolbar_title.text = resources.getString(R.string.title_movie_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as MovieMdl
        val drawable = BitmapDrawable(resources, BitmapFactory.decodeByteArray(movie.poster, 0, movie.poster?.size!!))
        iv_movie_poster.setImageDrawable(drawable)
        tv_movie_title.text = movie.title
        tv_movie_date.text = resources.getString(R.string.placeholder_release_date, movie.date)
        tv_movie_director.text = resources.getString(R.string.placeholder_director, movie.director)
        tv_movie_rating.text = resources.getString(R.string.placeholder_rating, movie.rating)
        tv_movie_overview.text = movie.overview
        rb_movies.max = 100
        rb_movies.progress = (movie.rating?.toInt()!!)
    }

}