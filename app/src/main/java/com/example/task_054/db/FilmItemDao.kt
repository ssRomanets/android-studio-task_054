package com.example.task_054.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task_054.models.FilmItem

@Dao
interface FilmItemDao {
    @Query("SELECT * FROM film_items ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPageList(limit: Int, offset: Int): List<FilmItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filmItem: FilmItem): Long
}