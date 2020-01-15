package com.stickearn.favoriteapp.helper

import android.provider.BaseColumns

/**
 * Created by devis on 2020-01-12
 */

object DatabaseContract {

    const val AUTHORITY = "com.stickearn.dicodingmadesubmission1"
    //const val SCHEME = "content"

    class MovieColumns : BaseColumns {

        companion object {

            const val TABLE_NAME = "movie_table"
            /*const val _ID = "id"
            const val TITLE = "title"
            const val OVERVIEW = "overview"
            const val RELEASE_DATE = "release_date"
            const val VOTE_AVERAGE = "vote_average"
            const val POSTER_PATH = "poster_path"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()*/

        }

    }

}