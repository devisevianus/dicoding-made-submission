package com.stickearn.favoriteapp.model

import androidx.annotation.NonNull
import java.io.Serializable

/**
 * Created by devis on 2019-12-08
 */

class MovieMdl : Serializable {

    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"

        /*fun fromContentValues(values: ContentValues?): MovieMdl {
            val movieMdl = MovieMdl()
            if (values != null && values.containsKey(COLUMN_ID)) {
                movieMdl.id = values.getAsLong(COLUMN_ID)
            }

            if (values != null && values.containsKey(COLUMN_TITLE)) {
                movieMdl.title = values.getAsString(COLUMN_TITLE)
            }

            return movieMdl
        }*/
    }

    @NonNull
    var id: Long = 0
    var title: String = ""
    var overview: String = ""
    var release_date: String? = null
    var vote_average: Double = 0.0
    var poster: String? = null

}