package com.stickearn.dicodingmadesubmission1.view.movie.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import com.stickearn.dicodingmadesubmission1.view.movie.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movies.*
import java.io.ByteArrayOutputStream

/**
 * Created by devis on 2019-12-25
 */

class MovieFragment : Fragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(): Fragment = MovieFragment()
    }

    private var movieList = arrayListOf<MovieMdl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        MovieDetailActivity.startThisActivity(
            context!!,
            movieList[position]
        )
    }

    private fun initListView() {
        val moviesTitle = resources.getStringArray(R.array.MoviesTitle)
        val moviesReleaseDate = resources.getStringArray(R.array.MoviesRelaseDate)
        val moviesRating = resources.getStringArray(R.array.MoviesRating)
        val moviesDirector = resources.getStringArray(R.array.MoviesDirector)
        val moviesOverview = resources.getStringArray(R.array.MoviesOverview)
        for (i in moviesTitle.indices) {
            val movieMdl = MovieMdl(
                convertToByteArray(loadTvShowsPoster()[i]!!),
                moviesTitle[i],
                moviesReleaseDate[i],
                moviesRating[i],
                moviesDirector[i],
                moviesOverview[i]
            )
            movieList.add(movieMdl)
        }

        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val adapter = MovieAdapter(movieList)
        rv_movie.apply {
            layoutManager = linearLayoutManager
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
        }
    }

    private fun loadTvShowsPoster(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.MoviesDrawable)
        val icons = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(context!!, id)
            }
        }
        ta.recycle()
        return icons
    }

    private fun convertToByteArray(drawable: Drawable): ByteArray {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        return stream.toByteArray()
    }

}