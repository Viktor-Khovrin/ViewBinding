package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class ExternalId(
    @SerializedName("imdb")
    val imdb: String?,
    @SerializedName("kpHD")
    val kpHD: String?,
    @SerializedName("tmdb")
    val tmdb: Int?
)