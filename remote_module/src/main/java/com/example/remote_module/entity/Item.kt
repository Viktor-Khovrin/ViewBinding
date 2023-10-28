package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("logo")
    val logo: LogoX,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)