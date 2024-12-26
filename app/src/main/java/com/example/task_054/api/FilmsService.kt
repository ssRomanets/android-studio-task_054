package com.example.task_054.data.api

import com.example.task_054.models.FilmsResponse
import com.example.task_054.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FilmsService {
    @Headers("X-API-KEY: ${API_KEY.toString()}")
    @GET("/api/v2.2/films")
    suspend fun getFilms(@Query("limit") limit: Int = 1): Response<FilmsResponse>
}