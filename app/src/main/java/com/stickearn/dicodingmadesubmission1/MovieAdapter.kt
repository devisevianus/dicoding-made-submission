package com.stickearn.dicodingmadesubmission1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Created by devis on 2019-12-08
 */

class MovieAdapter(context: Context, private val movieList: ArrayList<MovieMdl>) : ArrayAdapter<MovieMdl>(context, R.layout.item_movies, movieList) {

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)

        val moviePoster = view.findViewById<ImageView>(R.id.iv_movie_poster)
        val movieTitle = view.findViewById<TextView>(R.id.tv_movie_title)
        val movieDate = view.findViewById<TextView>(R.id.tv_movie_date)
        val movieRating = view.findViewById<TextView>(R.id.tv_movie_rating)

        moviePoster.setImageDrawable(loadMoviePoster(position))
        movieTitle.text = movieList[position].title
        movieDate.text = movieList[position].date
        movieRating.text = "Rating: ${movieList[position].rating}"

        return view
    }

    private fun loadMoviePoster(position: Int): Drawable? {
        val ta = context.resources.obtainTypedArray(R.array.MoviesDrawable)
        val poster = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                poster[i] = ContextCompat.getDrawable(context, id)
            }
        }
        ta.recycle()

        return poster[position]
    }

}