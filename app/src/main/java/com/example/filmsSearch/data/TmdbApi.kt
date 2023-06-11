package com.example.filmsSearch.data

import com.example.filmsSearch.data.Entity.TmdbResultsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApi {
    @GET("v1.3/movie")
    fun getFilms(
        @Header("X-API-KEY") apiKey: String,
//        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("top250") top250: String?,   // in top250 rating
//        @Query("year") year: String = "2023",
        @Query("ticketsOnSale") ticketsOnSale: String?,// filming
        @Query("genres.name") genres: String?,    // announced
        @Query("limit") limit: Int = 10
//        @Query("type") type: String = ""        // tv-series, tv-show
    ): Call<TmdbResultsDto>
}