package com.stickearn.dicodingmadesubmission1.view.tv.list

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.view.tv.detail.TvShowsDetailActivity
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2020-01-03
 */

class TvShowsAdapter(
    private val tvShowsList: ArrayList<TvShowsMdl>
) : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tvShowsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShowsList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowsMdl: TvShowsMdl) {
            with(itemView) {
                val drawable = BitmapDrawable(resources, BitmapFactory.decodeByteArray(tvShowsMdl.poster, 0, tvShowsMdl.poster?.size!!))
                iv_movie_poster.setImageDrawable(drawable)
                tv_movie_title.text = tvShowsMdl.title
                tv_movie_date.text = tvShowsMdl.date
                tv_movie_rating.text = itemView.context.resources.getString(R.string.placeholder_rating_, tvShowsMdl.rating)

                setOnClickListener {
                    TvShowsDetailActivity.startThisActivity(itemView.context, tvShowsMdl)
                }
            }
        }
    }

}