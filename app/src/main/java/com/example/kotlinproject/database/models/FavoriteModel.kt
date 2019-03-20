package com.example.kotlinproject.database.models

import com.google.gson.annotations.SerializedName

class FavoriteModel(

    @field:SerializedName("favorite_id")
    val favorite_id: Int,

    @field:SerializedName("category_name")
    val category_name: String = null ?: "",

    @field:SerializedName("photo_id")
    val photo_id: String = null ?: "",

    @field:SerializedName("title")
    val title: String? = null ?: "",

    @field:SerializedName("description")
    val description: String? = null ?: "",

    @field:SerializedName("imgUrl")
    val imgUrl: String? = null ?: "",

    @field:SerializedName("imgUrlHD")
    val imgUrlHD: String? = null ?: "",

    @field:SerializedName("latitude")
    val latitude: String? = null ?: "",

    @field:SerializedName("longitude")
    val longitude: String? = null ?: ""

)