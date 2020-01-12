package com.stickearn.dicodingmadesubmission1.view.tv.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.convertDate
import com.stickearn.dicodingmadesubmission1.helper.convertToLong
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import com.stickearn.dicodingmadesubmission1.view.tv.detail.TvShowsDetailActivity
import kotlinx.android.synthetic.main.item_movies.view.*

/**
 * Created by devis on 2020-01-03
 */

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {
    private var mTvShowsList = ArrayList<TvShowsMdl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mTvShowsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mTvShowsList[position])
    }

    fun setData(items: List<TvShowsMdl>) {
        mTvShowsList.clear()
        mTvShowsList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShowsMdl: TvShowsMdl) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${tvShowsMdl.poster}")
                    .error(R.drawable.img_noposter)
                    .into(iv_movie_poster)

                tv_movie_title.text = tvShowsMdl.name
                if (tvShowsMdl.first_air_date != null && tvShowsMdl.first_air_date != "") {
                    tv_movie_date.text = tvShowsMdl.first_air_date.convertToLong()?.convertDate()
                }
                tv_movie_rating.text = itemView.context.resources.getString(R.string.placeholder_rating_, tvShowsMdl.vote_average.toString())

                setOnClickListener {
                    TvShowsDetailActivity.startThisActivity(itemView.context, tvShowsMdl)
                }
            }
        }
    }

}