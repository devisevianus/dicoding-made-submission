package com.stickearn.favoriteapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.stickearn.favoriteapp.R
import com.stickearn.favoriteapp.helper.convertDate
import com.stickearn.favoriteapp.helper.convertToLong
import com.stickearn.favoriteapp.model.MovieMdl
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by devis on 2020-01-13
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

    private var movie: MovieMdl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        movie = intent.getSerializableExtra(EXTRA_MOVIE) as MovieMdl

        initView()
    }

    private fun initView() {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185/${movie?.poster}")
            .error(R.drawable.img_noposter)
            .into(iv_movie_poster)

        tv_movie_title.text = movie?.title
        if (movie?.release_date != null && movie?.release_date != "") {
            tv_movie_date.text = resources.getString(
                R.string.placeholder_release_date,
                movie?.release_date?.convertToLong()?.convertDate()
            )
        }
        tv_movie_rating.text = resources.getString(R.string.placeholder_rating, movie?.vote_average.toString())
        tv_movie_overview.text = movie?.overview
        rb_movies.max = 100
        rb_movies.progress = ((movie?.vote_average!! * 10).toInt())
    }

}