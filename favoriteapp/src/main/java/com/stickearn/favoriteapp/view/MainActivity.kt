package com.stickearn.favoriteapp.view

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stickearn.favoriteapp.R
import com.stickearn.favoriteapp.helper.DatabaseContract
import com.stickearn.favoriteapp.model.MovieMdl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOADER_MOVIES = 1
        val URI_MOVIE = Uri.parse(
            "content://${DatabaseContract.AUTHORITY}/${DatabaseContract.MovieColumns.TABLE_NAME}"
        )!!
    }

    private lateinit var mAdapter: MovieFavoriteAdapterV2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Favorite Movies"

        mAdapter = MovieFavoriteAdapterV2()

        LoaderManager.getInstance(this)
            .initLoader(LOADER_MOVIES,
                null,
                mLoaderCallback())

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        rv_movie.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }
    }

    private fun mLoaderCallback(): LoaderManager.LoaderCallbacks<Cursor> =
        object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(applicationContext,
                    URI_MOVIE,
                    arrayOf(MovieMdl.COLUMN_TITLE),
                    null, null, null)
            }

            override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                if (data?.count == 0) {
                    layout_empty.isVisible = true
                } else {
                    layout_empty.isVisible = false
                    mAdapter.setData(data)
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
                layout_empty.isVisible = true
                mAdapter.setData(null)
            }
        }

}
