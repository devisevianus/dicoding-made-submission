package com.stickearn.dicodingmadesubmission1.view.favorite.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.view.favorite.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_tv_shows.*

/**
 * Created by devis on 2020-01-11
 */

class TvFavoriteFragment : Fragment() {

    companion object {
        private const val TV_SHOWS_LIST = "tv_shows_list"
        fun newInstance(): Fragment = TvFavoriteFragment()
    }

    private lateinit var mViewModel: FavoriteViewModel
    private lateinit var mAdapter: TvFavoriteAdapter

    private var tvShowsList = arrayListOf<TvShowsMdl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        mAdapter = TvFavoriteAdapter()

        if (savedInstanceState == null) {
            mViewModel.tvShowsList.observe(this, Observer { tvShows ->
                tvShows.let {
                    tvShowsList.clear()
                    tvShowsList.addAll(it)
                    mAdapter.setData(tvShowsList)
                }
            })
        } else {
            val data = savedInstanceState.getString(TV_SHOWS_LIST)
            val movies = Gson().fromJson<List<TvShowsMdl>>(data, object : TypeToken<List<TvShowsMdl>>() {}.type)
            tvShowsList.clear()
            tvShowsList.addAll(movies)
            mAdapter.setData(tvShowsList)
        }

        initRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TV_SHOWS_LIST, Gson().toJson(tvShowsList))
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            context!!,
            LinearLayoutManager.VERTICAL,
            false
        )

        rv_tv_shows.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context!!, linearLayoutManager.orientation))
        }
    }

}