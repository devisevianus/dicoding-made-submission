package com.stickearn.dicodingmadesubmission1.view.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stickearn.dicodingmadesubmission1.helper.AppDatabase
import com.stickearn.dicodingmadesubmission1.helper.MovieRepository
import com.stickearn.dicodingmadesubmission1.helper.TvShowsRepository
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl

/**
 * Created by devis on 2020-01-11
 */

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val mMovieRepository: MovieRepository
    private val mTvRepository: TvShowsRepository
    val moviesList: LiveData<List<MovieMdl>>
    val tvShowsList: LiveData<List<TvShowsMdl>>

    init {
        val movieDao = AppDatabase.getDatabase(application).movieDao()
        val tvShowDao = AppDatabase.getDatabase(application).tvShowsDao()
        mMovieRepository = MovieRepository(movieDao)
        mTvRepository = TvShowsRepository(tvShowDao)
        moviesList = mMovieRepository.moviesList
        tvShowsList = mTvRepository.tvShowsList
    }

}