package com.stickearn.dicodingmadesubmission1.helper

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import kotlin.IllegalArgumentException

class MovieProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        /*val URI_MOVIE = Uri.parse(
            "content://${DatabaseContract.AUTHORITY}/${DatabaseContract.MovieColumns.TABLE_NAME}"
        )*/

        init {
            mUriMatcher.addURI(
                DatabaseContract.AUTHORITY,
                DatabaseContract.MovieColumns.TABLE_NAME,
                MOVIE
            )

            mUriMatcher.addURI(
                DatabaseContract.AUTHORITY,
                "${DatabaseContract.MovieColumns.TABLE_NAME}/*",
                MOVIE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val code = mUriMatcher.match(uri)
        if (code == MOVIE || code == MOVIE_ID) {
            val context = context ?: return null
            val movieDao = AppDatabase.getDatabase(context).movieDao()
            val cursor: Cursor
            cursor = if (code == MOVIE) {
                movieDao.selectAll()
            } else {
                movieDao.selectById(ContentUris.parseId(uri))
            }
            cursor.setNotificationUri(context.contentResolver, uri)

            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (mUriMatcher.match(uri)) {
            MOVIE -> {
                val context = context ?: return null

                val id = AppDatabase.getDatabase(context)
                    .movieDao()
                    .insert(MovieMdl.fromContentValues(values))
                context.contentResolver.notifyChange(uri, null)

                return ContentUris.withAppendedId(uri, id)
            }
            MOVIE_ID -> {
                throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (mUriMatcher.match(uri)) {
            MOVIE -> {
                throw IllegalArgumentException("Invalid URI, cannot update without ID $uri")
            }
            MOVIE_ID -> {
                val context = context ?: return 0

                val movieMdl = MovieMdl.fromContentValues(values)
                movieMdl.id = ContentUris.parseId(uri)
                val count = AppDatabase.getDatabase(context)
                    .movieDao()
                    .update(movieMdl)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (mUriMatcher.match(uri)) {
            MOVIE -> {
                throw IllegalArgumentException("Invalid URI, cannot update without ID $uri")
            }
            MOVIE_ID -> {
                val context = context ?: return 0

                val count = AppDatabase.getDatabase(context)
                    .movieDao()
                    .deleteMovieById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }
}
