package com.stickearn.dicodingmadesubmission1.view.movie

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stickearn.dicodingmadesubmission1.BuildConfig
import com.stickearn.dicodingmadesubmission1.api.ApiClient
import com.stickearn.dicodingmadesubmission1.api.ApiService
import com.stickearn.dicodingmadesubmission1.base.BaseViewState
import com.stickearn.dicodingmadesubmission1.helper.AppDatabase
import com.stickearn.dicodingmadesubmission1.helper.MovieRepository
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.model.MovieResponseMdl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by devis on 2020-01-05
 */

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val mListMovies = MutableLiveData<BaseViewState<List<MovieMdl>>>()
    val listMoviesResult: LiveData<BaseViewState<List<MovieMdl>>> = mListMovies

    private val mRepository: MovieRepository

    init {
        val movieDao = AppDatabase.getDatabase(application).movieDao()
        mRepository = MovieRepository(movieDao)
    }


    fun getListMovies() {
        mListMovies.value = BaseViewState.Loading
        val apiService = ApiClient().retrofitClient().create(ApiService::class.java)
        apiService.getMovieList(BuildConfig.API_KEY, "en-US")
            .enqueue(object : Callback<MovieResponseMdl> {
                override fun onFailure(call: Call<MovieResponseMdl>, t: Throwable) {
                    Log.e("onFailureGetMovies", t.message!!)
                    mListMovies.value = BaseViewState.Error(t.message)
                }

                override fun onResponse(
                    call: Call<MovieResponseMdl>,
                    response: Response<MovieResponseMdl>
                ) {
                    if (response.isSuccessful) {
                        mListMovies.value = BaseViewState.Success(response.body()?.results)
                    }
                }
            })
    }

    fun addMovieToFavorite(movieMdl: MovieMdl) = viewModelScope.launch {
        mRepository.addMovie(movieMdl)
    }

    fun findMovieFromFavorite(id: Long): LiveData<List<MovieMdl>>? {
        var movieLiveData: LiveData<List<MovieMdl>>? = null

        viewModelScope.launch {
            movieLiveData = mRepository.findMovie(id)
        }

        return movieLiveData
    }

    fun deleteMovieFromFavorite(movieMdl: MovieMdl) = viewModelScope.launch {
        mRepository.deleteMovie(movieMdl)
    }

}