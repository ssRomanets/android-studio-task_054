package com.example.task_054.data.api

import javax.inject.Inject

class FilmsRepository @Inject constructor(private val filmsService: FilmsService) {
    suspend fun getFilms(limitFilms: Int) = filmsService.getFilms(limitFilms)
}