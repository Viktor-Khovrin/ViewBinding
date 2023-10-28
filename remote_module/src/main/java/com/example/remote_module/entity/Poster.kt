package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("previewUrl")
    val previewUrl: String?,
    @SerializedName("url")
    val url: String?
)