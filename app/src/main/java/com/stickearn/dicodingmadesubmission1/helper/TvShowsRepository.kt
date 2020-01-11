package com.stickearn.dicodingmadesubmission1.helper

import androidx.lifecycle.LiveData
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl

/**
 * Created by devis on 2020-01-11
 */

class TvShowsRepository(private val tvShowsDao: TvShowsDao) {

    val tvShowsList: LiveData<List<TvShowsMdl>> = tvShowsDao.getTvShowsList()

    suspend fun addTvShows(tvShowsMdl: TvShowsMdl) {
        tvShowsDao.addTvShows(tvShowsMdl)
    }

    fun findTvShow(id: Long): LiveData<List<TvShowsMdl>>? {
        return tvShowsDao.findTvShow(id)
    }

    suspend fun deleteTvShow(tvShowsMdl: TvShowsMdl) {
        tvShowsDao.deleteTvShow(tvShowsMdl)
    }

}