package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("await")
    val await: Any?,
    @SerializedName("filmCritics")
    val filmCritics: Double,
    @SerializedName("imdb")
    val imdb: Double,
    @SerializedName("kp")
    val kp: Double,
    @SerializedName("russianFilmCritics")
    val russianFilmCritics: Double
)