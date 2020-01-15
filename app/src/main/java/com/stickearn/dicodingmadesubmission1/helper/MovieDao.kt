package com.stickearn.dicodingmadesubmission1.helper

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.stickearn.dicodingmadesubmission1.model.MovieMdl

/**
 * Created by devis on 2020-01-10
 */

@Dao
interface MovieDao {
    @Query("SELECT * from movie_table")
    fun getMoviesList(): LiveData<List<MovieMdl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieMdl: MovieMdl)

    @Query("SELECT * FROM movie_table WHERE id=:id")
    fun findMovie(id: Long): LiveData<List<MovieMdl>>?

    /*@Query("SELECT * from movie_table")
    fun getMoviesData(): Single<List<MovieMdl>>*/

    @Delete
    suspend fun deleteMovie(movieMdl: MovieMdl)

    /*@Query("DELETE from movie_table")
    suspend fun nukeTable()*/

    @Query("SELECT * from movie_table")
    fun selectAll(): Cursor

    @Query("SELECT * from movie_table WHERE id=:id")
    fun selectById(id: Long): Cursor

    @Insert
    fun insert(movieMdl: MovieMdl): Long

    @Update
    fun update(movieMdl: MovieMdl): Int

    @Query("DELETE from movie_table WHERE id=:id")
    fun deleteMovieById(id: Long): Int
}