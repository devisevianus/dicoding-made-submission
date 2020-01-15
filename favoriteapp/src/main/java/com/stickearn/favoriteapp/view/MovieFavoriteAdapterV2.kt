package com.stickearn.favoriteapp.view

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stickearn.favoriteapp.R
import com.stickearn.favoriteapp.helper.convertDate
import com.stickearn.favoriteapp.helper.convertToLong
import com.stickearn.favoriteapp.model.MovieMdl
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2020-01-13
 */

class MovieFavoriteAdapterV2 : RecyclerView.Adapter<MovieFavoriteAdapterV2.ViewHolder>() {
    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = if (mCursor == null) 0 else mCursor?.count!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mCursor?.moveToPosition(position)!!) {
            holder.bind(mCursor)
        }
    }

    fun setData(cursor: Cursor?) {
        mCursor = cursor
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cursor: Cursor?) {
            with(itemView) {
                val id = cursor?.getLong(cursor.getColumnIndexOrThrow(MovieMdl.COLUMN_ID))
                val title = cursor?.getString(cursor.getColumnIndexOrThrow(MovieMdl.COLUMN_TITLE))
                val date = cursor?.getString(cursor.getColumnIndexOrThrow("release_date"))
                val rating = cursor?.getDouble(cursor.getColumnIndexOrThrow("vote_average"))
                val poster = cursor?.getString(cursor.getColumnIndexOrThrow("poster"))
                val overview = cursor?.getString(cursor.getColumnIndexOrThrow("overview"))

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${poster}")
                    .error(R.drawable.img_noposter)
                    .into(iv_movie_poster)

                tv_movie_title.text = title
                tv_movie_date.text = date?.convertToLong()?.convertDate()
                tv_movie_rating.text = itemView.context.getString(R.string.placeholder_rating_, rating?.toString()!!)

                val movieMdl = MovieMdl()
                movieMdl.id = id!!
                movieMdl.title = title!!
                movieMdl.release_date = date!!
                movieMdl.vote_average = rating
                movieMdl.poster = poster
                movieMdl.overview = overview!!

                setOnClickListener {
                    MovieDetailActivity.startThisActivity(itemView.context, movieMdl)
                }
            }
        }
    }

}