package com.stickearn.dicodingmadesubmission1.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by devis on 2019-12-08
 */

class MovieResponseMdl(
    @SerializedName("results")
    val results: List<MovieMdl>
)

@Entity(tableName = "movie_table")
data class MovieMdl(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val release_date: String?,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("poster_path")
    val poster: String?
) : Serializable