package com.stickearn.dicodingmadesubmission1.view.tv

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stickearn.dicodingmadesubmission1.BuildConfig
import com.stickearn.dicodingmadesubmission1.api.ApiClient
import com.stickearn.dicodingmadesubmission1.api.ApiService
import com.stickearn.dicodingmadesubmission1.base.BaseViewState
import com.stickearn.dicodingmadesubmission1.helper.AppDatabase
import com.stickearn.dicodingmadesubmission1.helper.TvShowsRepository
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.model.TvShowsResponseMdl
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData



/**
 * Created by devis on 2020-01-05
 */

class TvShowsViewModel(application: Application) : AndroidViewModel(application) {
    private val mListTvShows = MutableLiveData<BaseViewState<List<TvShowsMdl>>>()
    val listTvShowsResult: LiveData<BaseViewState<List<TvShowsMdl>>> = mListTvShows

    private val mRepository: TvShowsRepository

    init {
        val tvShowsDao = AppDatabase.getDatabase(application).tvShowsDao()
        mRepository = TvShowsRepository(tvShowsDao)
    }

    fun getListTvShows(query: String? = null, type: String = "discover") {
        mListTvShows.value = BaseViewState.Loading
        val apiService = ApiClient().retrofitClient().create(ApiService::class.java)
        apiService.getTvShowsList(type, BuildConfig.API_KEY, "en-US", query)
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
                    } else {
                        mListTvShows.value = BaseViewState.Error(response.message())
                    }
                }
            })
    }

    fun addTvShowToFavorite(tvShowsMdl: TvShowsMdl) = viewModelScope.launch {
        mRepository.addTvShows(tvShowsMdl)
    }

    fun findTvShowFromFavorite(id: Long): LiveData<List<TvShowsMdl>>? {
        var tvLiveData: LiveData<List<TvShowsMdl>>? = null
        viewModelScope.launch {
            tvLiveData = mRepository.findTvShow(id)
        }

        return tvLiveData
    }

    fun deleteTvFromFavorite(tvShowsMdl: TvShowsMdl) = viewModelScope.launch {
        mRepository.deleteTvShow(tvShowsMdl)
    }

}