package com.stickearn.dicodingmadesubmission1.view.tv.list

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
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import java.io.ByteArrayOutputStream

/**
 * Created by devis on 2019-12-25
 */

class TvShowsFragment : Fragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(): Fragment =
            TvShowsFragment()
    }

    private var tvShowsList = arrayListOf<TvShowsMdl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    private fun initListView() {
        val tvShowsTitle = resources.getStringArray(R.array.TvShowsTitle)
        val tvShowsReleaseDate = resources.getStringArray(R.array.TvShowsReleaseDate)
        val tvShowsRating = resources.getStringArray(R.array.TvShowsRating)
        val tvShowsDirector = resources.getStringArray(R.array.TvShowsDirector)
        val tvShowsOverView = resources.getStringArray(R.array.TvShowsOverview)

        for (i in tvShowsTitle.indices) {
            val tvShowsMdl = TvShowsMdl(
                convertToByteArray(loadTvShowsPoster()[i]!!),
                tvShowsTitle[i],
                tvShowsReleaseDate[i],
                tvShowsRating[i],
                tvShowsDirector[i],
                tvShowsOverView[i]
            )
            tvShowsList.add(tvShowsMdl)
        }

        val linearLayoutManager = LinearLayoutManager(
            context!!,
            LinearLayoutManager.VERTICAL,
            false
        )
        val adapter = TvShowsAdapter(tvShowsList)
        rv_tv_shows.apply {
            layoutManager = linearLayoutManager
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context!!, linearLayoutManager.orientation))
        }
    }

    private fun loadTvShowsPoster(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.TvShowsDrawable)
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