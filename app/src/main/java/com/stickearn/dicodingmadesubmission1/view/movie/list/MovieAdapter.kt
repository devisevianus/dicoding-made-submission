package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.convertDate
import com.stickearn.dicodingmadesubmission1.helper.convertToLong
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2019-12-08
 */

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var mMovieList = ArrayList<MovieMdl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mMovieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mMovieList[position])
    }

    fun setData(items: List<MovieMdl>) {
        mMovieList.clear()
        mMovieList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieMdl: MovieMdl) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${movieMdl.poster}")
                    .error(R.drawable.img_noposter)
                    .into(iv_movie_poster)

                tv_movie_title.text = movieMdl.title
                if (movieMdl.release_date != null && movieMdl.release_date != "") {
                    tv_movie_date.text = movieMdl.release_date.convertToLong()?.convertDate()
                }
                tv_movie_rating.text = itemView.context.resources.getString(R.string.placeholder_rating_, movieMdl.vote_average.toString())

                setOnClickListener {
                    MovieDetailActivity.startThisActivity(itemView.context, movieMdl)
                }
            }
        }
    }

}