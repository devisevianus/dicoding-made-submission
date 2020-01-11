package com.stickearn.dicodingmadesubmission1.helper

import androidx.lifecycle.LiveData
import com.stickearn.dicodingmadesubmission1.model.MovieMdl

/**
 * Created by devis on 2020-01-10
 */

class MovieRepository(private val movieDao: MovieDao) {

    val moviesList: LiveData<List<MovieMdl>> = movieDao.getMoviesList()

    suspend fun addMovie(movieMdl: MovieMdl) {
        movieDao.addMovie(movieMdl)
    }

    fun findMovie(id: Long): LiveData<List<MovieMdl>>? {
        return movieDao.findMovie(id)
    }

    suspend fun deleteMovie(movieMdl: MovieMdl) {
        movieDao.deleteMovie(movieMdl)
    }

}