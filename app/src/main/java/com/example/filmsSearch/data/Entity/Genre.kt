package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String
)