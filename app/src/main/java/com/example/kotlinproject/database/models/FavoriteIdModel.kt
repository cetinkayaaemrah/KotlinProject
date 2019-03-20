package com.example.kotlinproject.database.models

import com.google.gson.annotations.SerializedName

class FavoriteIdModel(
    @field:SerializedName("favorite_id")
    val favorite_id: Int,

    @field:SerializedName("photo_id")
    val photo_id: String = null ?: ""

)