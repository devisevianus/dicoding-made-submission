package com.stickearn.dicodingmadesubmission1

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2019-12-08
 */

class MovieAdapter(context: Context, movieList: ArrayList<MovieMdl>) : ArrayAdapter<MovieMdl>(context, R.layout.item_movies, movieList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        }

        val holder = ViewHolder(view as View)
        val movieMdl = getItem(position) as MovieMdl

        holder.bind(movieMdl, position)

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

    // supaya smooth scrolling
    inner class ViewHolder constructor(private val itemView: View){
        fun bind(movieMdl: MovieMdl, position: Int) {
            with(itemView) {
                iv_movie_poster.setImageDrawable(loadMoviePoster(position))
                tv_movie_title.text = movieMdl.title
                tv_movie_date.text = movieMdl.date
                // pake placeholder biar lebih rapi dan menghindari penggunaan suppresslint
                tv_movie_rating.text = itemView.context.resources.getString(R.string.placeholder_rating_, movieMdl.rating)
            }
        }
    }

}