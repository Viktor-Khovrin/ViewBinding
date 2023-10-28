package com.example.remote_module

import com.example.remote_module.entity.TmdbResultsDto
import io.reactivex.rxjava3.core.Observable
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
    ): Observable<TmdbResultsDto>

    @GET("v1.3/movie")
    fun getFilmsFromSearch(
        @Header("X-API-KEY") apiKey: String,
//        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("top250") top250: String?,   // in top250 rating
//        @Query("year") year: String = "2023",
        @Query("ticketsOnSale") ticketsOnSale: String?,// filming
        @Query("genres.name") genres: String?,    // announced
        @Query("name") query: String?, //Query to server for search request
        @Query("limit") limit: Int = 10
//        @Query("type") type: String = ""        // tv-series, tv-show
    ): Observable<TmdbResultsDto>

}