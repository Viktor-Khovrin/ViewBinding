package com.example.filmsSearch.data.Entity


import com.google.gson.annotations.SerializedName

data class Watchability(
    @SerializedName("items")
    val items: List<Item>?
)