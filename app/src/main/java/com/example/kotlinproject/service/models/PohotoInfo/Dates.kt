package com.example.kotlinproject.service.Retrofit.Datas.PohotoInfo

import com.google.gson.annotations.SerializedName

data class Dates(

    @field:SerializedName("taken")
    val taken: String? = null ?: "",

    @field:SerializedName("takengranularity")
    val takengranularity: String? = null ?: "",

    @field:SerializedName("lastupdate")
    val lastupdate: String? = null ?: "",

    @field:SerializedName("takenunknown")
    val takenunknown: String? = null ?: "",

    @field:SerializedName("posted")
    val posted: String? = null ?: ""
)