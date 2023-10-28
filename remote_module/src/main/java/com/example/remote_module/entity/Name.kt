package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String?
)