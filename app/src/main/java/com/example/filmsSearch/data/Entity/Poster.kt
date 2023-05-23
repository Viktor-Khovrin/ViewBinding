package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("previewUrl")
    val previewUrl: String,
    @SerializedName("url")
    val url: String
)