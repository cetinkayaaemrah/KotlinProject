package com.example.kotlinproject.database.models

import com.google.gson.annotations.SerializedName

class CategoryModel (

    @field:SerializedName("category_id")
    val category_id: Int,
    @field:SerializedName("category_name")
    val category_name: String = null ?: ""
)
