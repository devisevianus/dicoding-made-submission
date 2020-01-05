package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2019-12-08
 */

class MovieAdapter(private val movieList: ArrayList<MovieMdl>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieMdl: MovieMdl) {
            with(itemView) {
                val drawable = BitmapDrawable(resources, BitmapFactory.decodeByteArray(movieMdl.poster, 0, movieMdl.poster?.size!!))
                iv_movie_poster.setImageDrawable(drawable)
                tv_movie_title.text = movieMdl.title
                tv_movie_date.text = movieMdl.date
                tv_movie_rating.text = itemView.context.resources.getString(R.string.placeholder_rating_, movieMdl.rating)

                setOnClickListener {
                    MovieDetailActivity.startThisActivity(itemView.context, movieMdl)
                }
            }
        }
    }

}