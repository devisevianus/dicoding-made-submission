package com.stickearn.dicodingmadesubmission1.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.MovieBannerWidget
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.AppDatabase
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by devis on 2020-01-12
 */

internal class StackRemoteViewsFactory(
    private val mContext: Context
) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<MovieMdl>()
    private val mItems = ArrayList<String?>()

    override fun onCreate() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        getMoviesData()
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185/${mWidgetItems[position].poster}")
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
        return if (mWidgetItems.isNotEmpty()) {
            mWidgetItems.size
        } else {
            0
        }
    }

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }

    @SuppressLint("CheckResult")
    private fun getMoviesData() {
        AppDatabase.getDatabase(mContext).movieDao().getMoviesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { movies ->
                for (movie in movies) {
                    Log.i("MOVIE", movie.title)
                    val movieMdl = MovieMdl(
                        movie.id,
                        movie.title,
                        movie.overview,
                        movie.release_date,
                        movie.vote_average,
                        movie.poster
                    )
                    //mItems.add(movie.poster)
                }
                mWidgetItems.addAll(movies)
                Log.d("moviesSize", movies.size.toString())
            }
    }
}