package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class Watchability(
    @SerializedName("items")
    val items: List<Item>?
)