package com.stickearn.dicodingmadesubmission1.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by devis on 2019-12-25
 */

class TvShowsResponseMdl(
    @SerializedName("results")
    val results: List<TvShowsMdl>
)

class TvShowsMdl(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("first_air_date")
    val first_air_date: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("poster_path")
    val poster: String
) : Serializable