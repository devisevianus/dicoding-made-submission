package com.stickearn.dicodingmadesubmission1.api

import com.stickearn.dicodingmadesubmission1.model.MovieResponseMdl
import com.stickearn.dicodingmadesubmission1.model.TvShowsResponseMdl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by devis on 2020-01-05
 */

interface ApiService {

    @GET("{type}/movie")
    fun getMovieList(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String? = null
    ): Call<MovieResponseMdl>

    @GET("{type}/tv")
    fun getTvShowsList(
        @Path("type") type: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String? = null
    ): Call<TvShowsResponseMdl>

    @GET("discover/movie")
    fun getMovieRelease(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") primaryDateGte: String,
        @Query("primary_release_date.lte") primaryDateLte: String
    ): Call<MovieResponseMdl>

}