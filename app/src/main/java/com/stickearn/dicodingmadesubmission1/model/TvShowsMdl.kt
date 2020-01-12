package com.stickearn.dicodingmadesubmission1.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by devis on 2019-12-25
 */

class TvShowsResponseMdl(
    @SerializedName("results")
    val results: List<TvShowsMdl>
)

@Entity
data class TvShowsMdl(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    val id: Long,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    val first_air_date: String?,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val vote_average: Double,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val poster: String
) : Serializable