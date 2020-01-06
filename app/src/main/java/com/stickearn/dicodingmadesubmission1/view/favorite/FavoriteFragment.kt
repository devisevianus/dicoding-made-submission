package com.stickearn.dicodingmadesubmission1.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.view.movie.list.MovieFragment
import com.stickearn.dicodingmadesubmission1.view.tv.list.TvShowsFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * Created by devis on 2020-01-05
 */

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = FavoriteAdapter(activity?.supportFragmentManager)
        pagerAdapter.add(MovieFragment.newInstance(), resources.getString(R.string.title_movies))
        pagerAdapter.add(TvShowsFragment.newInstance(), resources.getString(R.string.title_tv_shows))

        vp_content.adapter = pagerAdapter
        tablayout_content.setupWithViewPager(vp_content)
    }

}