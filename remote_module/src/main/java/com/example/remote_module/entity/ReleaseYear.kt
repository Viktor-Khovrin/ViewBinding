package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class ReleaseYear(
    @SerializedName("end")
    val end: Int?,
    @SerializedName("start")
    val start: Int
)