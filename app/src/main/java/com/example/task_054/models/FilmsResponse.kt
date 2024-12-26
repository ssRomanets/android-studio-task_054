package com.example.task_054.models

data class FilmsResponse (
    val total: Int,
    val totalPages: Int,
    val items: List<Film>
)