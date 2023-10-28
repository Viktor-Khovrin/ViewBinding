package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name")
    val name: String
)