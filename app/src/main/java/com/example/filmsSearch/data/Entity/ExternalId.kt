package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class ExternalId(
    @SerializedName("imdb")
    val imdb: String?,
    @SerializedName("kpHD")
    val kpHD: String?,
    @SerializedName("tmdb")
    val tmdb: Int?
)