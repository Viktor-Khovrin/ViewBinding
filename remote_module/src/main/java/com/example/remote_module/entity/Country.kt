package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: String
)