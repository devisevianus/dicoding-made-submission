package com.stickearn.dicodingmadesubmission1.view.tv.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stickearn.dicodingmadesubmission1.BuildConfig
import com.stickearn.dicodingmadesubmission1.api.ApiClient
import com.stickearn.dicodingmadesubmission1.api.ApiService
import com.stickearn.dicodingmadesubmission1.base.BaseViewState
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.model.TvShowsResponseMdl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by devis on 2020-01-05
 */

class TvShowsViewModel : ViewModel() {
    private val mListTvShows = MutableLiveData<BaseViewState<List<TvShowsMdl>>>()
    val listTvShowsResult: LiveData<BaseViewState<List<TvShowsMdl>>> = mListTvShows

    fun getListTvShows() {
        mListTvShows.value = BaseViewState.Loading
        val apiService = ApiClient().retrofitClient().create(ApiService::class.java)
        apiService.getTvShowsList(BuildConfig.API_KEY, "en-US")
            .enqueue(object : Callback<TvShowsResponseMdl> {
                override fun onFailure(call: Call<TvShowsResponseMdl>, t: Throwable) {
                    Log.e("onFailureGetTVShows", t.message!!)
                    mListTvShows.value = BaseViewState.Error(t.message)
                }

                override fun onResponse(
                    call: Call<TvShowsResponseMdl>,
                    response: Response<TvShowsResponseMdl>
                ) {
                    if (response.isSuccessful) {
                        mListTvShows.value = BaseViewState.Success(response.body()?.results)
                    }
                }
            })
    }

}