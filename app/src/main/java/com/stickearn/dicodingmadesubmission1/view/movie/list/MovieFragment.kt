package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.base.BaseViewState
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by devis on 2019-12-25
 */

class MovieFragment : Fragment() {

    companion object {
        private const val MOVIE_LIST = "movie_list"
        fun newInstance(): Fragment = MovieFragment()
    }

    private lateinit var mViewModel: MovieViewModel
    private lateinit var mAdapter: MovieAdapter

    private var movieList = arrayListOf<MovieMdl>()
    private var mSearchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        mAdapter = MovieAdapter()

        if (savedInstanceState == null) {
            mViewModel.getListMovies()
        } else {
            val data = savedInstanceState.getString(MOVIE_LIST)
            val movies = Gson().fromJson<List<MovieMdl>>(data, object : TypeToken<List<MovieMdl>>() {}.type)
            movieList.clear()
            movieList.addAll(movies)
            mAdapter.setData(movieList)
        }

        initObserve()
        initListView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            mSearchView = searchItem.actionView as SearchView
        }

        if (mSearchView != null) {
            mSearchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty()!!) {
                        mViewModel.getListMovies(newText, "search")
                    } else {
                        mViewModel.getListMovies()
                    }
                    Log.i("onQueryTextChange", newText)
                    return true
                }
            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MOVIE_LIST, Gson().toJson(movieList))
    }

    private fun initObserve() {
        mViewModel.apply {
            listMoviesResult.observe(this@MovieFragment, Observer {
                when (it) {
                    is BaseViewState.Loading -> {
                        showLoading(true)
                    }
                    is BaseViewState.Success -> {
                        showLoading(false)
                        movieList.clear()
                        movieList.addAll(it.data!!)
                        mAdapter.setData(movieList)
                    }
                    is BaseViewState.Error -> {
                        showLoading(false)
                        if (it.errorMessage.toString().contains("unable", ignoreCase = true)) {
                            Toast.makeText(context!!, resources.getString(R.string.message_no_connection), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context!!, resources.getString(R.string.message_failed_load_movies), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun initListView() {
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

    private fun showLoading(state: Boolean) {
        pb_content.isVisible = state
    }

}