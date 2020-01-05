package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stickearn.dicodingmadesubmission1.BuildConfig
import com.stickearn.dicodingmadesubmission1.api.ApiClient
import com.stickearn.dicodingmadesubmission1.api.ApiService
import com.stickearn.dicodingmadesubmission1.base.BaseViewState
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.model.MovieResponseMdl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by devis on 2020-01-05
 */

class MovieViewModel : ViewModel() {
    private val mListMovies = MutableLiveData<BaseViewState<List<MovieMdl>>>()
    val listMoviesResult: LiveData<BaseViewState<List<MovieMdl>>> = mListMovies

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

}