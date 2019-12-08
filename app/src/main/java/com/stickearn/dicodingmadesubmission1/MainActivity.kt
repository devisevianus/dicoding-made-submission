package com.stickearn.dicodingmadesubmission1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var movieList = arrayListOf<MovieMdl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_toolbar_title.text = resources.getString(R.string.title_movie)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initListView()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        MovieDetailActivity.startThisActivity(this, movieList[position])
    }

    private fun initListView() {
        val moviesTitle = resources.getStringArray(R.array.MoviesTitle)
        val moviesReleaseDate = resources.getStringArray(R.array.MoviesRelaseDate)
        val moviesRating = resources.getStringArray(R.array.MoviesRating)
        val moviesDirector = resources.getStringArray(R.array.MoviesDirector)
        val moviesOverview = resources.getStringArray(R.array.MoviesOverview)
        for (i in moviesTitle.indices) {
            val movieMdl = MovieMdl(
                i,
                moviesTitle[i],
                moviesReleaseDate[i],
                moviesRating[i],
                moviesDirector[i],
                moviesOverview[i]
            )
            movieList.add(movieMdl)
        }

        val adapter = MovieAdapter(this, movieList)
        lv_movie.adapter = adapter
        lv_movie.onItemClickListener = this
    }
}
