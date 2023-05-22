package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class ReleaseYear(
    @SerializedName("end")
    val end: Int?,
    @SerializedName("start")
    val start: Int
)