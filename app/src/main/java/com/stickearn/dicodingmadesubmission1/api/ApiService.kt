package com.stickearn.dicodingmadesubmission1.api

import com.stickearn.dicodingmadesubmission1.model.MovieResponseMdl
import com.stickearn.dicodingmadesubmission1.model.TvShowsResponseMdl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by devis on 2020-01-05
 */

interface ApiService {

    @GET("movie")
    fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieResponseMdl>

    @GET("tv")
    fun getTvShowsList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<TvShowsResponseMdl>

}