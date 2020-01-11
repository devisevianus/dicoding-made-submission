package com.stickearn.dicodingmadesubmission1.helper

import androidx.lifecycle.LiveData
import androidx.room.*
import com.stickearn.dicodingmadesubmission1.model.TvShowsMdl

/**
 * Created by devis on 2020-01-11
 */

@Dao
interface TvShowsDao {
    @Query("SELECT * from tvshowsmdl")
    fun getTvShowsList(): LiveData<List<TvShowsMdl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(tvShowsMdl: TvShowsMdl)

    @Query("SELECT * from tvshowsmdl where id=:id LIMIT 1")
    fun findTvShow(id: Long): LiveData<List<TvShowsMdl>>?

    @Delete
    suspend fun deleteTvShow(tvShowsMdl: TvShowsMdl)

    @Query("DELETE from tvshowsmdl where id=:id")
    suspend fun deleteTvShow(id: Long)

    @Query("DELETE from tvshowsmdl")
    suspend fun nukeTable()
}