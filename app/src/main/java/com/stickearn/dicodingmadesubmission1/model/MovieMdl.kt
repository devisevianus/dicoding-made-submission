package com.stickearn.dicodingmadesubmission1.model

import android.content.ContentValues
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
class MovieMdl : Serializable {

    companion object {
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"

        fun fromContentValues(values: ContentValues?): MovieMdl {
            val movieMdl = MovieMdl()
            if (values != null && values.containsKey(COLUMN_ID)) {
                movieMdl.id = values.getAsLong(COLUMN_ID)
            }

            if (values != null && values.containsKey(COLUMN_TITLE)) {
                movieMdl.title = values.getAsString(COLUMN_TITLE)
            }

            return movieMdl
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("title")
    var title: String = ""
    @SerializedName("overview")
    var overview: String = ""
    @SerializedName("release_date")
    var release_date: String? = null
    @SerializedName("vote_average")
    var vote_average: Double = 0.0
    @SerializedName("poster_path")
    var poster: String? = null

}