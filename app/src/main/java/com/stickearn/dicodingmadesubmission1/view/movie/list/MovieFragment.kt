package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by devis on 2019-12-25
 */

class MovieFragment : Fragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(): Fragment = MovieFragment()
    }

    private var movieList = arrayListOf<MovieMdl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        MovieDetailActivity.startThisActivity(
            context!!,
            movieList[position]
        )
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

        val adapter =
            MovieAdapter(context!!, movieList)
        lv_movie.adapter = adapter
        lv_movie.onItemClickListener = this
    }

}