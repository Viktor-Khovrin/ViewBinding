package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("logo")
    val logo: LogoX,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)