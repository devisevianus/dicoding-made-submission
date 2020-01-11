package com.stickearn.dicodingmadesubmission1.view.movie.detail

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
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.MovieViewModel
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

    private lateinit var mViewModel: MovieViewModel

    private var isChecked = false
    private var movie: MovieMdl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movie = intent.getSerializableExtra(EXTRA_MOVIE) as MovieMdl

        mViewModel.findMovieFromFavorite(movie?.id!!)?.observe(this, Observer { movieList ->
            if (movieList != null && movieList.isNotEmpty()) {
                isChecked = true
                toggle_favorite.isChecked = isChecked
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
        tv_toolbar_title.text = resources.getString(R.string.title_movie_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle_favorite.isVisible = true
        toggle_favorite.setOnClickListener {
            isChecked = !isChecked
            actionMovieFavorite(isChecked)
        }
    }

    private fun initView() {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185/${movie?.poster}")
            .into(iv_movie_poster)

        tv_movie_title.text = movie?.title
        tv_movie_date.text = resources.getString(R.string.placeholder_release_date, movie?.release_date?.convertToLong()?.convertDate())
        tv_movie_rating.text = resources.getString(R.string.placeholder_rating, movie?.vote_average.toString())
        tv_movie_overview.text = movie?.overview
        rb_movies.max = 100
        rb_movies.progress = ((movie?.vote_average!! * 10).toInt())
    }

    private fun actionMovieFavorite(isChecked: Boolean) {
        if (isChecked) {
            mViewModel.addMovieToFavorite(movie!!)
            Toast.makeText(
                this,
                resources.getString(R.string.message_add_to_favorite),
                Toast.LENGTH_SHORT).show()
        } else {
            mViewModel.deleteMovieFromFavorite(movie!!)
            Toast.makeText(
                this,
                resources.getString(R.string.message_remove_from_favorite),
                Toast.LENGTH_SHORT).show()
        }
    }

}