package com.example.task_054.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_items")
data class FilmItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val posterUrl: String,
    val nameFilm: String,
    val type: String,
)
