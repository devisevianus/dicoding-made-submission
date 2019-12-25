package com.stickearn.dicodingmadesubmission1.view.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stickearn.dicodingmadesubmission1.R

/**
 * Created by devis on 2019-12-25
 */

class TvShowsFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment = TvShowsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}