package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Locality(

    @field:SerializedName("woeid")
    val woeid: String? = null ?: "",

    @field:SerializedName("_content")
    val content: String? = null?:"",

    @field:SerializedName("place_id")
    val placeId: String? = null?:""
)