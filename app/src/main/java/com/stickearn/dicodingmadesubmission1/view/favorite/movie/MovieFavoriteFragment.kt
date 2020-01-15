package com.stickearn.dicodingmadesubmission1.view.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.favorite.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by devis on 2020-01-11
 */

class MovieFavoriteFragment : Fragment() {

    companion object {
        private const val MOVIE_LIST = "movie_list"
        //private const val LOADER_MOVIES = 1
        fun newInstance(): Fragment = MovieFavoriteFragment()
    }

    private lateinit var mViewModel: FavoriteViewModel
    private lateinit var mAdapter: MovieFavoriteAdapter
    //private lateinit var mAdapter: MovieFavoriteAdapterV2

    private var moviesList = arrayListOf<MovieMdl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        mAdapter = MovieFavoriteAdapter()
        //mAdapter = MovieFavoriteAdapterV2()

        if (savedInstanceState == null) {
            mViewModel.moviesList.observe(this, Observer { movies ->
                movies.let {
                    moviesList.clear()
                    moviesList.addAll(it)
                    if (moviesList.isEmpty()) {
                        rv_movie.isVisible = false
                        layout_empty.isVisible = true
                    } else {
                        layout_empty.isVisible = false
                        rv_movie.isVisible = true
                        mAdapter.setData(moviesList)
                    }
                }
            })
            /*LoaderManager.getInstance(this)
                .initLoader(LOADER_MOVIES, null, mLoaderCallback())*/
        } else {
            val data = savedInstanceState.getString(MOVIE_LIST)
            val movies = Gson().fromJson<List<MovieMdl>>(data, object : TypeToken<List<MovieMdl>>() {}.type)
            moviesList.clear()
            moviesList.addAll(movies)
            mAdapter.setData(moviesList)
        }

        initRecyclerView()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MOVIE_LIST, Gson().toJson(moviesList))
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        rv_movie.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }
    }

    /*private fun mLoaderCallback(): LoaderManager.LoaderCallbacks<Cursor> =
        object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(context!!,
                    MovieProvider.URI_MOVIE,
                    arrayOf(MovieMdl.COLUMN_TITLE),
                    null, null, null)
            }

            override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                mAdapter.setData(data)
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
                mAdapter.setData(null)
            }
        }*/

}