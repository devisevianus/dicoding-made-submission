package com.stickearn.dicodingmadesubmission1.util

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.stickearn.dicodingmadesubmission1.model.MovieMdl


/**
 * Created by devis on 2020-01-12
 */

internal class StackRemoteViewsFactory(
    private val mContext: Context
) : RemoteViewsService.RemoteViewsFactory {

    //private val mWidgetItems = ArrayList<MovieMdl>()
    //private val mItems = ArrayList<String?>()
    private var cursor: Cursor? = null

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long {
        return if (cursor?.moveToPosition(position)!!) {
            cursor?.getLong(0)!!
        } else {
            position.toLong()
        }
    }

    override fun onDataSetChanged() {
        //getMoviesData()

        if (cursor != null) {
            cursor?.close()
        }

        val identityToken = Binder.clearCallingIdentity()
        cursor = mContext.contentResolver.query(CONTENT_URI, null, null, null, null)
        Binder.restoreCallingIdentity(identityToken)

    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews? {
        if (position == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor?.moveToPosition(position)!!) {
            return null
        }

        val movieMdl = getMovieData(position)

        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        try {
            /*val bitmap = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185/${mWidgetItems[position].poster}")
                .submit(500, 500)
                .get()*/

            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185/${movieMdl.poster}")
                .submit(500, 500)
                .get()
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val extras = bundleOf(MovieBannerWidget.EXTRA_ITEM to position)
        val fillIntnet = Intent()
        fillIntnet.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntnet)
        return rv
    }

    override fun getCount(): Int {
        /*return if (mWidgetItems.isNotEmpty()) {
            mWidgetItems.size
        } else {
            0
        }*/

        return if (cursor == null) 0 else cursor?.count!!
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }

    /*@SuppressLint("CheckResult")
    private fun getMoviesData() {
        AppDatabase.getDatabase(mContext).movieDao().getMoviesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movies ->
                for (movie in movies) {
                    Log.i("MOVIE", movie.title)
                    val movieMdl = MovieMdl()
                    movieMdl.id = movie.id
                    movieMdl.title = movie.title
                    movieMdl.overview = movie.overview
                    movieMdl.release_date = movie.release_date
                    movieMdl.vote_average = movie.vote_average
                    movieMdl.poster = movie.poster

                    //mItems.add(movie.poster)
                }
                mWidgetItems.addAll(movies)
                Log.d("moviesSize", movies.size.toString())
            }
    }*/

    private fun getMovieData(position: Int): MovieMdl {
        require(cursor?.moveToPosition(position)!!) { "position invalid!" }

        val movieMdl = MovieMdl()
        val poster = cursor?.getString(cursor?.getColumnIndexOrThrow("poster")!!)
        movieMdl.poster = poster
        return movieMdl
    }
}